import requests
import Adafruit_ADS1x15

from haptic_controller import HapticController

adc_hand = Adafruit_ADS1x15.ADS1015(address=0x49, busnum=1)
adc_leg = Adafruit_ADS1x15.ADS1015(address=0x4A, busnum=1)

controller = HapticController()

GAIN = 1

grain_volume_multiplier = 1

right_feedback_volume = 0
left_feedback_volume = 0

# Auxiliary Variables
rh_fsr_mod = 0
lh_fsr_mod = 0
rh_flex_mod = 0
lh_flex_mod = 0
rl_flex_mod = 0
ll_flex_mod = 0


'''
LRA CHANNEL POSITION
-----------------------
0 : Right Leg
1 : Left Leg
2 : Right Wrist
3 : Left Wrist
4 : Right Elbow
5 : Left Elbow
6 : Right Ear
7 : Left Ear
'''
RIGHT_LEG_LRA = 0
LEFT_LEG_LRA = 1
RIGHT_WRIST_LRA = 2
LEFT_WRIST_LRA = 3
RIGHT_ELBOW_LRA = 4
LEFT_ELBOW_LRA = 5
RIGHT_EAR_LRA = 6
LEFT_EAR_LRA = 7


'''
SENSOR DATA FORMAT
-----------------------
RH : Right Hand
LH : Left Hand
RL : Right Leg
LL : Left Leg

values_hand : [RH_FSR, LH_FSR, RH_FLEX, LH_FLEX]
values_leg  : [RL_FLEX, LL_FLEX]

'''
def read_sensor_data(verbose=False):
    # Reading Sensors on Hand
    values_hand = [0, 0, 0, 0]
    for i in range(4):
        values_hand[i] = adc_hand.read_adc(i, gain=GAIN)
    
    # Reading Sensors on Leg
    values_leg = [0, 0]
    for i in range(2):
        values_leg[i] = adc_leg.read_adc(i, gain=GAIN)
    
    if verbose:
        print(values_hand, values_leg)
    
    return values_hand, values_leg

def get_volume(mod_value):
    volume = mod_value/10
    volume = 1000 * pow(volume, 2)
    return int(volume)
    

def generate_feedback(values_hand, values_leg, verbose=False):
    global right_feedback_volume, left_feedback_volume
    
    # Wrist Feedback
    global rh_fsr_mod, lh_fsr_mod
    
    rh_fsr, lh_fsr = values_hand[0], values_hand[1]
    
    if int(rh_fsr/100) != rh_fsr_mod:
        rh_fsr_mod = int(rh_fsr/100)
        right_feedback_volume = get_volume(rh_fsr_mod)
        controller.set_grain_volume(RIGHT_WRIST_LRA, grain_volume_multiplier * right_feedback_volume)
        controller.play_grain(RIGHT_WRIST_LRA)
        if verbose:
            print("RW " + str(rh_fsr))
    
    if int(lh_fsr/100) != lh_fsr_mod:
        lh_fsr_mod = int(lh_fsr/100)
        left_feedback_volume = get_volume(lh_fsr_mod)
        controller.set_grain_volume(LEFT_WRIST_LRA, grain_volume_multiplier * left_feedback_volume)
        controller.play_grain(LEFT_WRIST_LRA)
        if verbose:
            print("LW " + str(lh_fsr))
    
    
    # Elbow Feedback
    global rh_flex_mod, lh_flex_mod
    
    rh_flex, lh_flex = values_hand[2], values_hand[3]
    
    if int(rh_flex/10) != rh_flex_mod:
        rh_flex_mod = int(rh_flex/10)
        controller.set_grain_volume(RIGHT_ELBOW_LRA, grain_volume_multiplier * right_feedback_volume)
        controller.play_grain(RIGHT_ELBOW_LRA)
        if verbose:
            print("RE " + str(rh_flex))
        
    if int(lh_flex/10) != lh_flex_mod:
        lh_flex_mod = int(lh_flex/10)
        controller.set_grain_volume(LEFT_ELBOW_LRA, grain_volume_multiplier * left_feedback_volume)
        controller.play_grain(LEFT_ELBOW_LRA)
        if verbose:
            print("LE " + str(lh_flex))
    
    
    # Leg Feedback
    global rl_flex_mod, ll_flex_mod
    
    rl_flex, ll_flex = values_leg[0], values_leg[1]
    
    if int(rl_flex/10) != rl_flex_mod:
        rl_flex_mod = int(rl_flex/10)
        controller.set_grain_volume(RIGHT_LEG_LRA, grain_volume_multiplier * right_feedback_volume * 10)
        controller.play_grain(RIGHT_LEG_LRA)
        if verbose:
            print("RL " + str(rl_flex))
        
    if int(ll_flex/10) != ll_flex_mod:
        ll_flex_mod = int(ll_flex/10)
        controller.set_grain_volume(LEFT_LEG_LRA, grain_volume_multiplier * left_feedback_volume * 10)
        controller.play_grain(LEFT_LEG_LRA)
        if verbose:
            print("LL " + str(ll_flex))
        
def update_grain_volume_multiplier():
    global grain_volume_multiplier
    try:
        f = open("grain_volume_multiplier.config", "r")
        grain_volume_multiplier = float(f.read())
        f.close()
    except Exception as e:
        print("Sensor Reader Error: File I/O Exception " + e)
        grain_volume_multiplier = 1

def main():
    while True:
        update_grain_volume_multiplier()
        values_hand, values_leg = read_sensor_data(False)
        generate_feedback(values_hand, values_leg, False)
        

if __name__ == "__main__":
    main()