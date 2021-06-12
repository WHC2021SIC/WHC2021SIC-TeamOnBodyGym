import os

class HapticController():
    
    def __init__(self, port=3000):
        self.PUREDATA_PORT = port
        self.PUREDATA_MAP = [4, 3, 6, 5, 8, 7, 2, 1]

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

    Commands
    a. Stop Music Playback    : 0
    b. Play Predefined Music  : 1 [0 - 4]
         Argument is an integer value, predefined music is defined by numbers 0 through 4.
    c. Play Custom Music      : 1 5
         Plays custom music only if it is loaded in the system.
    d. Set Music Volume             : 2 [0.00 - 1.00]
         Argument is a float value, 0 mutes the volume.
    e. Disable DSP            : 3 0
    f. Enable DSP             : 3 1
    g. Play Grain:            : 4 [3 - 8]
         Plays grain on the specified audio channel between 3 and 8
    d. Set Grain Volume       : 5 [0.00 - 1000.00]
         Argument is a float value, 0 mutes the volume.

    '''

    def stop_music(self):
        self.send_command("0")

    def play_music(self, music_idx):
        self.send_command("1 " + str(music_idx));

    def set_music_volume(self, volume):
        self.send_command("2 " + str(volume))

    def disable_dsp(self):
        self.send_command("3 0")
        
    def enable_dsp(self):
        self.send_command("3 1")
    
    def play_grain(self, channel):
        channel = self.PUREDATA_MAP[int(channel)]
        self.send_command("4 " + str(channel))
    
    def set_grain_volume(self, volume):
        self.send_command("5 " + str(volume))
