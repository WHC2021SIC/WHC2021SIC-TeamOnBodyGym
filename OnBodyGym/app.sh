#!/bin/sh
puredata haptic_generator.pd &
python3 server.py &
python3 sensor_reader.py &
echo 'Now Sleeping' &
sleep 2 &
echo 'Woke Up' &
echo '3 1;' | pdsend 3000
