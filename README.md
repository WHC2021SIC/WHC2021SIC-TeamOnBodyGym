# On-Body Gym
**Feel the Vibe: A Wearable On-Body Gym Simulator**

![Project Banner](https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/art/FeelTheVibeBanner.png)

![Concept Art](https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/architecture/front_side_integrated.png)

## Abstract

Impacted by the COVID-19 pandemic, gyms across the globe have either shut down or restricted total capacity. Due to this, everyone has turned to exercising at home regularly. To benefit people with their mental and physical health, we propose a wearable on-body haptic device capable of providing a holistic workout experience at home. Using granular haptic feedback, our proposed system can simulate the feeling of using actual gym equipment. We leverage flex sensors, force sensors, and vibrotactile actuators, all working in tandem to detect workout activity and trigger a granular haptic response to mirror the feedback we receive from various spring-and-pulley based gym machines. The generated granular haptic feedback also motivates free-hand workout routines. Additionally, our system simulates the rhythm of the music using vibrotactile actuators to promote endurance and a positive mood during workouts, allowing users to select from a range of predefined soundtracks, or load their own music. This will be especially useful for people with hearing impairment, who can now enjoy the rhythm of any music while working out. Overall, we have designed this system to encourage all types of people to pursue fitness and to enrich their home workout experience.

## Introduction
Introduction goes here.

## Contents

- [Abstract](#abstract)
- [Introduction](#introduction)
- [Getting Started](#getting-started)
    + [Step 1: Acquiring the Hardware](#step-1-acquiring-the-hardware)
    + [Step 2: Preparing the Hardware](#step-2-preparing-the-hardware)
    + [Step 3: Hardwares, Assemble!](#step-3-hardwares-assemble)
    + [Step 4: Put 'em in a Box, Tie 'em with a Ribbon](#step-4-put-em-in-a-box-tie-em-with-a-ribbon)
- [A User's Guide to Wearing the On-Body Gym](#a-users-guide-to-wearing-the-on-body-gym)
- [Interacting with the On-Body Gym](#interacting-with-the-on-body-gym)
- [On-Body Jukebox](#on-body-jukebox)
- [On-Body VR](#on-body-vr)
- [A Developer's Guide to the On-Body Gym APIs](#a-developers-guide-to-the-on-body-gym-apis)
- [Authors](#authors)
- [Acknowledgements](#acknowledgements)
- [License](#license)


# Getting Started

Want to experience On-Body Gym for yourself? Follow our step-by-step guide to build your own On-Body Gym system!

## Step 1: Acquiring the Hardware

![Primary Hardware Components](https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/architecture/PrimaryHardware.png)

The hardware requirements for building the On-Body Gym are categorised as follows:

#### Primary Electronic Components
* [Raspberry Pi 4 Microcontroller 8GB](https://www.raspberrypi.org/products/raspberry-pi-4-model-b/)
* [Audio Injector Octo 8-Channel Sound Card](http://www.audioinjector.net/rpi-octo-hat)
* [Syntacts 8-Channel Amplifier Board](https://www.syntacts.org)

#### Sensors, Actuators, and Microcontroller Modules
* [SparkFun Qwiic Hat for Raspberry Pi](https://www.sparkfun.com/products/14459)
* [SparkFun Qwiic 12-Bit 4-Channel ADC](https://www.sparkfun.com/products/15334) (2 count)
* [Force Sensitive Resistors 0.5"](https://www.sparkfun.com/products/9375) (2 count)
* [Flex Sensor 4.5"](https://www.sparkfun.com/products/8606) (4 count)
* [Coin Linear Resonant Actuator](https://www.vibration-motor.com/coin-vibration-motors/coin-linear-resonant-actuators-lra/g1040003d) (8 count)

#### For Connecting Things Together
* [2-RCA Male to 3.5mm Male](https://www.amazon.com/eBoot-3-5mm-Audio-Stereo-Adapter/dp/B06XC5KWJN/) (4 count) <br>
    - **Cable Length Recommendation:** Short, approximately 6 inches in length.
* [Qwiic Cable](https://www.sparkfun.com/products/14426) (2 count) <br>
    - **Cable Length Recommendation:** Extremely short, approximately 50 mm in length.
* [Ribbon Cables](https://www.sparkfun.com/products/10647) <br>
    - **Cable Recommentation:** A multi-colored wire strand setup will help in cable management. <br>
    - **Cable Length Recommendation:** Extremely long, at least 30 feet long for a 10-wire pack.
* [Female Jumper Wires](https://www.amazon.com/40pcs-Female-2-54mm-Jumper-2x40pcs/dp/B00GSE2S98) (16 count) <br>
    - **Cable Length Recommendation:** As short as can be, only the connector is required with minimal wire (will be soldered to ribbon cables).
* **[OPTIONAL]** [Type-C Cable](https://www.amazon.com/Charger-Braided-Charging-Compatible-Samsung/dp/B08NX7M6MS) <br>
    - **Cable Recommentation:** This cable is required only if using a power bank or USB outlet to power Raspberry Pi. <br>
    - **Cable Length Recommendation:** Depends, approximately 2 feet if using a powerbank, otherwise as long as possible if powering through USB-outlet.
* **[OPTIONAL]** [Micro-HDMI to HDMI Cable](https://www.raspberrypi.org/products/micro-hdmi-to-standard-hdmi-a-cable/) <br>
    - **Cable Recommentation:** This cable is required only if connection to monitor is required. <br>
    - **Cable Length Recommendation:** As per monitor placement.
* **[OPTIONAL]** [2x AA Battery Holder + Jumper Headers](https://www.adafruit.com/product/3858) <br>
    - **Recommentation:** This is only required to power the Syntacts board. Alternatively, a USB cable with exposed VCC and GND wires can also be used along with a power bank or USB-outlet. <br>

#### Other Electronic Components
* [1k Ohm Resistor](https://www.sparkfun.com/products/14492) (6 count)
* **[OPTIONAL]** [20000mAh Power Bank](https://www.amazon.com/Portable-26800mah-Ultra-High-High-Performance-Smartphone/dp/B07ZX22KJS) <br>
    - **Recommendation:** This is HIGHLY RECOMMENDED in order to allow for an untethered experience.

#### Non-Electronic Components for Assembling the System
* [Knee-Length Socks](https://www.amazon.com/gp/product/B07H2WGKVB) (at least 4 pairs)
* [Velcro Cable Ties](https://www.amazon.com/gp/product/B001E1Y5O6) (approximately 30 pieces) <br>
    - **Recommendation:** Cable Ties and Socks are used to hold components in place. These are highly recommended, but alternatively any string with significant tensile strength can be used to tie the components on the user's body, not recommended though.
* [Band-Aid](https://www.amazon.com/gp/product/B00ZQF0Y5K) (each setup needs approximately 10 band-aids per use, purchase accordingly) <br>
    - **Recommendation:** Band-Aids are used for holding components against the skin since they are considered as skin-safe adhesives, but alternatively regular tapes could also be used, not recommended though.
* Cardboard Box (17mm L x 11mm B x 9mm D)

#### Tools
Assembling the On-Body Gym will require the usual workshop tools which include (but may not be limited to): scissor, screw-drivers, blade, soldering kit, shrink tubes (optional, for wrapping up the soldered-joints), and a heat gun (optional, for shrinking the shrink tube). A cheaper alternative to shrink tubes and heat gun is an electrical tape, which can also be used to wrap up soldered joints.

## Step 2: Preparing the Hardware
Before we start connecting the hardware together, we need to prepare some components first.

#### Changing ADC Board I2C Address
The SparkFun 12-bit 4-channel ADC board has a default I2C address of 0x48. However, this address may conflict with other components on the Raspberry Pi board. Thankfully, the ADC board offers 3 additional I2C address to choose from (0x49, 0x4A, and 0x4B). Thus, in order to avoid an address conflict, we need to manually change the I2C address of both the ADC boards. Follow [this tutorial](https://learn.sparkfun.com/tutorials/qwiic-12-bit-adc-hookup-guide/hardware-overview) (under Jumpers > I2C Address) to learn how to change I2C address of this ADC board. Based on this tutorial, first cut the connection to the 0x48 address for both the ADC boards (remember we have 2 counts of the ADC board), then make connection to address 0x49 for one of the ADC boards, and another connection to address 0x4A for the other ADC boards.

#### Disconnecting the 10kΩ Potentiometer of the ADC Boards
The ADC boards have a 10k ohm potentiometer connected to their AIN3 ports which disturbs the sensor readings. Thus, we need to disconnect the potentiometer. Follow [this tutorial](https://learn.sparkfun.com/tutorials/qwiic-12-bit-adc-hookup-guide/hardware-overview) (under Jumpers > Potentiometer) to learn more about how to cut this potentiometer.

#### Adding Females Jumpers to the Linear Resonant Actuators
From the ribbon cable, take out 8 sets of 2-wired cables with length of at least 1 meter. Slightly peel off both ends of the cables to expose the wires. Solder one end of each of the 2-wired cable ends with a linear resonant actuator. Now, cut the female-to-female jumper wires in half, and peel off the ends of the cables to expose the wire. Now, for each of the 8 sets of 2-wired ribbon cables soldered to the linear resonant actuators, solder the other end of the cable with the jumper wires. This will result in 8 linear resonant actuator assemblies, with each actuator having two female jumpers at the end of a 1 meter long cable. If done correctly, the end result should look similar to the linear resonant actuator assembly shown [here](https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/architecture/PrimaryHardware.png?raw=true).

* **Recommendation:** While taking out 8 sets of 1 meter 2-wired cables, try to take out 4 sets of 2 meter 2-wired cables first, each pair having a different color combination, and then cut these 4 sets in half to make 8 sets of 1 meter 2-wired cables. Doing this will result in each color combination have 2 sets of 1 meter 2-wired cables, which will help immensely with wire management in the later part of the build.
* **Recommendation:** It is highly recommended to use shrink tubes at the solder joints and heat it with a heat gun. This will not only protect the system from short-circuits and strengthen the overall structure of the system but will also make the system more comfortable to mount at a later stage.

#### Soldering Wires to Flex and Force Sensors
Similar to the previous step involving the linear resonant actuators, we need to add 1-meter long 2-wired cables to each of the 4 flex and 2 force sensors. However, unlike the previous step, we do not need to solder female jumpers on the other end of the cable.
* **Recommendation:** Similar to the previous stage, it is highly recommended to use shrink tubes at the solder joints and heat it with a heat gun. This will not only protect the system from short-circuits and strengthen the overall structure of the system but will also make the system more comfortable to mount at a later stage.

## Step 3: Hardwares, Assemble!
Once the hardware components are prepared, it is now finally time to assemble them!

![Sensor Wiring](https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/architecture/SensorWiring.png)

## Step 4: Put 'em in a Box, Tie 'em with a Ribbon
Now that the hardware is assembled together, we can finally compactify the setup by putting it in a cardboard box.

# A User's Guide to Wearing the On-Body Gym
This section contains step-by-step instructions on how to wear the On-Body Gym system.

# Interacting with the On-Body Gym
This section contains description of some of the interactions supported by the On-Body Gym system.

# On-Body Jukebox
This section contains description about the features of the On-Body Jukebox Android application.

# On-Body VR
This section contains description about the features of the On-Body Gym virtual reality application.

# A Developer's Guide to the On-Body Gym APIs
This section contains description about the APIs of the On-Body Gym server.

# Authors
This project has been made possible through a collaboration of some amazing people and organization!

## Team

#### Md Aashikur Rahman Azim

<img alt="Aashik's picture" src="https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/portraits/Aashik.jpg?raw=true" width="100px" height="auto"/>

Md Aashikur Rahman Azim is a third-year computer science Ph.D. student at the University of Virginia (UVa). Aashik is currently working with Professor Seongkook Heo on wearable user interfaces in the context of Human-Computer Interaction (HCI). He received both Bachelor of Science and Master of Science degrees from the Department of Computer Science and Engineering at Bangladesh University of Engineering and Technology (BUET) in February 2013 and December 2016, respectively. His research area includes Human-Computer Interaction, Applied Machine Learning, and Embedded Systems.

Find more information on his [website](https://sites.google.com/view/aashikazim/).

#### Archana Narayanan

<img alt="Archana's picture" src="https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/portraits/Archana.jpg?raw=true" width="100px" height="auto"/>

Archana Narayanan is an MS student in Computer Engineering at the University of Virginia, Charlottesville. She is also a part of the Human Computer Interaction Lab at UVA where her current research focuses on the development of a multidimensional haptic feedback device based on asymmetric vibrations. Her research interests include Embedded Systems, Hardware Engineering and Human Computer Interaction. Prior to joining the master’s program, she was a project assistant at the Indian Institute of Science, Bangalore where she built data acquisition systems for spectroscopy using FPGA’s. In the past, she has worked on multiple projects involving FPGA’s,IoT,analog and digital devices/circuits, multiple sensors, microcontrollers, and actuators. 

Find more information on her [website](https://archana95in.wixsite.com/archananarayanan).

#### Adil Rahman

<img alt="Adil's picture" src="https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/portraits/Adil.jpg?raw=true" width="100px" height="auto"/>

Adil Rahman is a first-year PhD student at the University of Virginia (UVa). Adil's research interest lies in the field of Human-Computer Interaction, particularly in making devices more accessible to everyone and creating new interaction techniques. Prior to joining the PhD program at UVa, Adil has had a year's worth of experience working with gesture recognition at the Indian Statistical Institute. Adil also holds a BTech (IT) from Heritage Institute of Technology, Kolkata, from where he graduated as a gold-medalist in 2020. In his spare time, Adil loves to play video games, make music, and then play some more video games!

Explore Adil's work on his [GitHub](https://github.com/adildsw) page and additional information on his [CV](https://adildsw.github.io/docs/Adil_Rahman_s_Resume.pdf).

#### Wen Ying

<img alt="Wen's picture" src="https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/portraits/Wen.jpeg?raw=true" width="100px" height="auto"/>

Wen Ying is a first-year CS PhD student at the University of Virginia. Her study involves experimenting with different types of haptic feedback from which she has had experience with creating virtual haptic feedback using LRA actuators, sensors, boards, etc.

Check her [portfolio](https://drive.google.com/file/d/1v74Rjerr8_QPv-6RMAg9XmglLobY8OH5/view?usp=sharing) for more information.

## Advisor

#### Seongkook Heo

<img alt="Seongkook's picture" src="https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/portraits/Seongkook.jpg?raw=true" width="100px" height="auto"/>

Seongkook Heo is an Assistant Professor in the Department of Computer Science at the University of Virginia. His research interests span across various areas of human-computer interaction, with an emphasis on interaction techniques and technologies. He was previously a postdoctoral researcher in the DGP Lab at the University of Toronto, where he worked with Prof. Daniel Wigdor. He received his PhD in Computer Science at the KAIST in 2017, advised by Prof. Geehyuk Lee. He was also a research intern at industry research labs, including Samsung Advanced Institute of Technology, Microsoft Research (with Dr. Ken Hinckley), and Autodesk Research (with Dr. Tovi Grossman).

Find more information on his [website](https://seongkookheo.com).

## Chairs

#### Christian Frisson

<img alt="Christian Frissons's picture" src="https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/portraits/ChristianFrisson.jpg?raw=true" width="100px" height="auto"/>

Christian Frisson is an associate researcher at the Input Devices and Music Interaction Laboratory (IDMIL) (2021), previously postdoctoral researcher at McGill University with the IDMIL (2019-2020), at the University of Calgary with the Interactions Lab (2017-2018) and at Inria in France with the Mjolnir team (2016-2017). He obtained his PhD at the University of Mons, numediart Institute, in Belgium (2015); his MSc in “Art, Science, Technology” from Institut National Polytechnique de Grenoble with the Association for the Creation and Research on Expression Tools (ACROE), in France (2006); his Masters in Electrical (Metrology) and Mechanical (Acoustics) Engineering from ENSIM in Le Mans, France (2005). 
Christian Frisson is a researcher in Human-Computer Interaction, with expertise in Information Visualization, Multimedia Information Retrieval, and Tangible/Haptic Interaction. Christian creates and evaluates user interfaces for manipulating multimedia data. Christian favors obtaining replicable, reusable and sustainable results through open-source software, open hardware and open datasets. 
With his co-authors, Christian obtained the IEEE VIS 2019 Infovis Best Paper award and was selected among 4 finalists for IEEE Haptics Symposium 2020 Most Promising WIP.

Find more information on his [website](https://frisson.re).

#### Jun Nishida

<img alt="Jun Nishida's picture" src="https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/portraits/JunNishida.jpg?raw=true" width="100px" height="auto"/>

Jun Nishida is **Currently** Postdoctoral Fellow at University of Chicago & Research Fellow at Japan Society for the Promotion of Science (JSPS PDRA) / **Previously** JSPS Research Fellow (DC1), Project Researcher at Japanese Ministry of Internal Affairs and Communications, SCOPE Innovation Program & PhD Fellow at Microsoft Research Asia / Graduated from Empowerment Informatics Program, University of Tsukuba, Japan. 

I’m a postdoctoral fellow at University of Chicago. I have received my PhD in Human Informatics at University of Tsukuba, Japan in 2019. I am interested in designing experiences in which all people can maximize and share their physical and cognitive capabilities to support each other. I explore the possibility of this interaction in the field of rehabilitation, education, and design. To this end, I design wearable cybernic interfaces which share one’s embodied and social perspectives among people by means of electrical muscle stimulation, exoskeletons, virtual/augmented reality systems. Received more than 40 awards including Microsoft Research Asia Fellowship Award, national grants, and three University Presidential Awards. Review service at ACM SIGCHI, SIGGRAPH, UIST, TEI, IEEE VR, HRI.

Find more information on their [website](https://junis.sakura.ne.jp/wp).

#### Heather Culbertson

<img alt="Heather Culbertson's picture" src="https://github.com/WHC2021SIC/WHC2021SIC-TeamOnBodyGym/blob/master/images/portraits/HeatherCulbertson.jpg?raw=true" width="100px" height="auto"/>

Heather Culbertson is a Gabilan Assistant Professor of Computer Science at the University of Southern California. Her research focuses on the design and control of haptic devices and rendering systems, human-robot interaction, and virtual reality. Particularly she is interested in creating haptic interactions that are natural and realistically mimic the touch sensations experienced during interactions with the physical world. Previously, she was a research scientist in the Department of Mechanical Engineering at Stanford University where she worked in the Collaborative Haptics and Robotics in Medicine (CHARM) Lab. She received her PhD in the Department of Mechanical Engineering and Applied Mechanics (MEAM) at the University of Pennsylvania in 2015 working in the Haptics Group, part of the General Robotics, Automation, Sensing and Perception (GRASP) Laboratory. She completed a Masters in MEAM at the University of Pennsylvania in 2013, and earned a BS degree in mechanical engineering at the University of Nevada, Reno in 2010. She is currently serving as the Vice-Chair for Information Dissemination for the IEEE Technical Committee on Haptics. Her awards include a citation for meritorious service as a reviewer for the IEEE Transactions on Haptics, Best Paper at UIST 2017, and the Best Hands-On Demonstration Award at IEEE World Haptics 2013.

Find more information on her [website](https://sites.usc.edu/culbertson/).


# Acknowledgements

SIC chairs would like to thank Evan Pezent, Zane A. Zook and Marcia O'Malley from [MAHI Lab](http://mahilab.rice.edu) at Rice University for having distributed to them 2 [Syntacts](https://www.syntacts.org) kits for the [IROS 2020 Intro to Haptics for XR Tutorial](http://iros-haptics-tutorial.org/). 
SIC co-chair Christian Frisson would like to thank Edu Meneses and Johnty Wang from [IDMIL](http://idmil.org) at McGill University for their recommendations on Raspberry Pi hats for audio and sensors.

Team On-Body Gym would like to thank [Kuntal Podder](https://kuntal013.artstation.com) for making the hand-grip 3D model for the virtual reality application. We would also like to thank the WHC 2021 SIC chairs for providing us with the opportunity (and of course the amazing hardware kit)!


# License

This documentation is released under the terms of the Creative Commons Attribution Share Alike 4.0 International license (see [LICENSE.txt](LICENSE.txt)).
