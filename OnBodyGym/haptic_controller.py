import os

class HapticController():
    
    def __init__(self, port=3000):
        self.PUREDATA_PORT = port
        self.PUREDATA_MAP = [1, 2, 3, 4, 5, 6, 7, 8]
        self.SYNTACTS_MUSIC_CH1 = 6
        self.SYNTACTS_MUSIC_CH2 = 7
    
    def get_pd_mapped_channel(self, channel):
        return self.PUREDATA_MAP[channel]
    
    def update_pd_map(self, pd_map):
        assert len(pd_map) == 8, "PureData map must be of length 8."
        for i in pd_map:
            assert type(pd_map[i]) == int, "PureData map must contain only integer values."
            assert pd_map[i] > 0 and pd_map[i] < 9, "PureData map must only contain channels 1 through 8."
        self.PUREDATA_MAP = pd_map

    def send_command(self, cmd):
        os.system("echo '" + cmd + ";' | pdsend " + str(self.PUREDATA_PORT))

    '''

    Command Interface for Haptic Generator
    ---------------------------------------
    File
    haptic_generator.pd

    Command Format
    <Route> <Argument>

    Interface Routes
    A. Stop Music Playback: 0
    B. Play Music: 1
    C. Set Volume: 2
    D. Toggle DSP: 3
    E. Play Grain: 4
    F. Set Grain Volume: 5
    G. Toggle Test Tone: 6

    Commands
    a. Stop Music Playback    : 0
    b. Play Predefined Music  : 1 [1 - 8] [1 - 8] [0 - 4]
         Arguments are three integer values, first two being between 1 and 8 defining output channel, and the last one being the predefined music defined by numbers 0 through 4.
    c. Play Custom Music      : 1 [1 - 8] [1 - 8] 5
         Plays custom music only if it is loaded in the system, first two arguments same as above.
    d. Set Music Volume             : 2 [0.00 - 1.00]
         Argument is a float value, 0 mutes the volume.
    e. Disable DSP            : 3 0
    f. Enable DSP             : 3 1
    g. Play Grain:            : 4 [1 - 8]
         Plays grain on the specified audio channel between 1 and 8
    h. Set Grain Volume       : 5 [1 - 8] [0.00 - 1000.00]
         First argument is an integer value containing output channel between 1 and 8, second argument is a float value, 0 mutes the volume.
    i. Toggle Test Tone       : 6 [1 - 8] [0 - 1]
         First argument defines output channel between 1 and 8, the second argument defines test tone as ON (1) or OFF (0)

    '''

    def stop_music(self):
        self.send_command("0")

    def play_music(self, music_idx, channel_1=-1, channel_2=-1):
        if channel_1 == -1 or channel_2 == -1:
            channel_1 = self.SYNTACTS_MUSIC_CH1
            channel_2 = self.SYNTACTS_MUSIC_CH2
        ch1 = self.PUREDATA_MAP[channel_1]
        ch2 = self.PUREDATA_MAP[channel_2]
        self.send_command("1 {} {} {}".format(ch1, ch2, music_idx));

    def set_music_volume(self, volume):
        self.send_command("2 " + str(volume))

    def disable_dsp(self):
        self.send_command("3 0")
        
    def enable_dsp(self):
        self.send_command("3 1")
    
    def restart_dsp(self):
        self.disable_dsp()
        self.enable_dsp()
    
    def play_grain(self, channel):
        channel = self.PUREDATA_MAP[int(channel)]
        self.send_command("4 " + str(channel))
    
    def set_grain_volume(self, channel, volume):
        channel = self.PUREDATA_MAP[int(channel)]
        print("5 " + str(channel) + " " + str(volume))
        self.send_command("5 " + str(channel) + " " + str(volume))
    
    def enable_test_tone(self, channel):
        self.send_command("6 " + str(channel) + " 1");
    
    def disable_test_tone(self, channel):
        self.send_command("6 " + str(channel) + " 0");
    
    def disable_all_test_tones(self):
        for i in range(1, 9):
            self.disable_test_tones(i)
