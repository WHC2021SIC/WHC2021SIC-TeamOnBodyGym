from os import path
from flask import Flask, request, render_template

from haptic_controller import HapticController
from net_utils import get_ip
from sensor_reader import read_sensor_data

HOST = get_ip()
PORT = 11996

FSR_MAX = 1100

grain_volume_multiplier = 1

app = Flask(__name__, template_folder="web", static_folder="web")
controller = HapticController()

@app.route("/")
def home():
    return "OnBodyGym Server"

@app.route("/play_music", methods=["GET"])
def play_music():
    music_idx = request.args.get("music_idx")
    if music_idx == "5":
        if not path.exists("jukebox/5.wav"):
            return "File Does Not Exist"
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

@app.route("/stop_music")
def stop_music():
    controller.enable_dsp()
    controller.stop_music()
    return "OK"

@app.route("/uploader")
def uploader():
    return render_template("uploader.html")

@app.route("/upload_custom_audio", methods=["POST"])
def upload_custom_audio():
    file = request.files['file']
    file.save("jukebox/5.wav")
    return "Music Uploaded Successfully!"

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
    volume = volume * grain_volume_multiplier
    controller.enable_dsp()
    for ch in channel:
        controller.set_grain_volume(ch, volume)
    return "OK"

@app.route("/reset_dsp")
def reset_dsp():
    controller.enable_dsp()
    return "OK"

@app.route("/get_sensor_values")
def get_sensor_values():
    values_hand, values_leg = read_sensor_data()
    values = values_hand + values_leg
    values = [str(item) for item in values]
    return ",".join(values)

@app.route("/get_normalized_fsr_values")
def get_normalized_fsr_values():
    values, _ = read_sensor_data()
    return "{},{}".format(values[0]/FSR_MAX, values[1]/FSR_MAX)

@app.route("/set_grain_volume_multiplier", methods=["GET"])
def set_grain_volume_multiplier():
    global grain_volume_multiplier
    volume = request.args.get("volume")
    if not volume:
        volume = 100
    volume = float(volume) / 100
    grain_volume_multiplier = volume
    try:
        f = open("grain_volume_multiplier.config", "w")
        f.write(str(grain_volume_multiplier))
        f.close()
    except:
        print("Server Error: File I/O Exception Occurred")
    return "OK"

def run_server(host=HOST, port=PORT):
    controller.enable_dsp()
    app.run(host=host, port=port)
    
if __name__ == "__main__":
    run_server()
