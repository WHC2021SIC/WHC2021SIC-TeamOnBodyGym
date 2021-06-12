from flask import Flask, request

from haptic_controller import HapticController
from net_utils import get_ip

HOST = get_ip()
PORT = 11996

app = Flask(__name__)
controller = HapticController()

@app.route("/")
def home():
    return "OnBodyGym Server"

@app.route("/play", methods=["GET"])
def play():
    music_idx = request.args.get("music_idx")
    volume = request.args.get("volume")
    if not volume:
        volume = 70
    volume = float(volume) / 100
    volume = pow(volume, 2)    
    controller.enable_dsp()
    controller.set_music_volume(volume)
    controller.play_music(music_idx)
    return "OK"

@app.route("/stop")
def stop():
    controller.enable_dsp()
    controller.stop_music()
    return "OK"

@app.route("/set_music_volume", methods=["GET"])
def set_music_volume():
    volume = request.args.get("volume")
    if not volume:
        volume = 70
    volume = float(volume) / 100
    volume = pow(volume, 2)
    controller.enable_dsp()
    controller.set_music_volume(volume)
    return "OK"

@app.route("/play_grain", methods=["GET"])
def play_grain():
    channel = request.args.get("channel")
    if not channel:
        return "NaN"
    controller.enable_dsp()
    controller.play_grain(channel)
    return "OK"

@app.route("/set_grain_volume", methods=["GET"])
def set_grain_volume():
    volume = request.args.get("volume")
    if not volume:
        volume = 70
    volume = float(volume) / 100
    volume = 1000 * pow(volume, 2)
    controller.enable_dsp()
    controller.set_grain_volume(volume)
    return "OK"

def main():
    app.run(host=HOST, port=PORT)
    
if __name__ == "__main__":
    main()
