import socket

TEST_IP = "8.8.8.8"

def get_ip():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.connect((TEST_IP, 0))
    ip = s.getsockname()[0]
    return ip
