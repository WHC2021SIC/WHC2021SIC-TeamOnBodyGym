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
def play_music():
    music_idx = request.args.get("music_idx")
    volume = request.args.get("volume")
    channel_1 = request.args.get("channel_1")
    channel_2 = request.args.get("channel_2")
    if not volume:
        volume = 70
    volume = float(volume) / 100
    volume = pow(volume, 2)
    controller.enable_dsp()
    controller.set_music_volume(volume)
    if channel_1 and channel_2:
        controller.play_music(music_idx, channel_1, channel_2)
    else:
        controller.play_music(music_idx)
    return "OK"

@app.route("/stop")
def stop_music():
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
    channel = request.args.get("channel")
    if not volume:
        volume = 70
    if not channel:
        channel = [1, 2, 3, 4, 5, 6, 7, 8]
    else:
        channel = list(channel)
        
    volume = float(volume) / 100
    volume = 1000 * pow(volume, 2)
    controller.enable_dsp()
    for ch in channel:
        controller.set_grain_volume(ch, volume)
    return "OK"

def run_server(host=HOST, port=PORT):
    controller.enable_dsp()
    app.run(host=host, port=port)
    
if __name__ == "__main__":
    run_server()
