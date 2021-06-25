using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.Networking;

public class HandGripBehaviour : MonoBehaviour
{
    public TextMeshPro debugTextN;
    public TextMeshPro debugTextS;
    public TextMeshPro debugTextE;
    public TextMeshPro debugTextW;

    public GameObject rightGripBone;
    public GameObject leftGripBone;

    [Range(0, 1)]
    public float rightGripRotation = 0;
    [Range(0, 1)]
    public float leftGripRotation = 0;

    Vector3 rightRot;
    Vector3 leftRot;

    private const int SAMPLE_COUNT = 3;

    private float[] rightGripRotationSamples = new float[SAMPLE_COUNT];
    private float[] leftGripRotationSamples = new float[SAMPLE_COUNT];

    private int gripIdx = 0;

    string result = "";

    const int gripRotZMin = 130;
    const int gripRotZMax = 160;

    private string api = "http://172.27.98.40:11996/get_normalized_fsr_values";
    private const string def_ip = "192.168.0.2";
    private const string def_port = "11996";

    private void Start()
    {
        for (int i = 0; i < SAMPLE_COUNT; i++)
        {
            rightGripRotationSamples[i] = 0;
            leftGripRotationSamples[i] = 0;
        }
        string ip = INIWorker.IniReadValue(INIWorker.Sections.main, INIWorker.Keys.ip);
        Debug.Log(ip);
        if (ip == null)
        {
            ip = def_ip;
            INIWorker.IniWriteValue(INIWorker.Sections.main, INIWorker.Keys.ip, ip);
        }
        string port = INIWorker.IniReadValue(INIWorker.Sections.main, INIWorker.Keys.port);
        if (port == null)
        {
            port = def_port;
            INIWorker.IniWriteValue(INIWorker.Sections.main, INIWorker.Keys.port, port);
        }

        api = "http://" + ip + ":" + port + "/get_normalized_fsr_values";
        InvokeRepeating("UpdateForceData", 1.0f, 0.1f);
    }

    private void Update()
    {
        rightRot = rightGripBone.transform.localEulerAngles;
        rightRot.z = gripRotZMin + (float)(rightGripRotation * (gripRotZMax - gripRotZMin));
        rightGripBone.transform.localEulerAngles = rightRot;

        leftRot = leftGripBone.transform.localEulerAngles;
        leftRot.z = gripRotZMin + (float)(leftGripRotation * (gripRotZMax - gripRotZMin));
        leftGripBone.transform.localEulerAngles = leftRot;
    }

    private void UpdateForceData()
    {
        StartCoroutine(GetRequest(api));
    }

    private float getGripAvg(float[] samples)
    {
        float sum = 0;
        for (int i = 0; i < SAMPLE_COUNT; i++)
        {
            sum += samples[i];
        }
        return sum / SAMPLE_COUNT;
    }

    private void showError(string error)
    {
        debugTextN.text = "ERROR: " + error;
        debugTextS.text = "ERROR: " + error;
        debugTextE.text = "ERROR: " + error;
        debugTextW.text = "ERROR: " + error;
        debugTextN.color = UnityEngine.Color.red;
        debugTextS.color = UnityEngine.Color.red;
        debugTextE.color = UnityEngine.Color.red;
        debugTextW.color = UnityEngine.Color.red;
    }

    private void showSuccess()
    {
        debugTextN.text = "On-Body VR: Connected To Server";
        debugTextS.text = "On-Body VR: Connected To Server";
        debugTextE.text = "On-Body VR: Connected To Server";
        debugTextW.text = "On-Body VR: Connected To Server";
        debugTextN.color = UnityEngine.Color.green;
        debugTextS.color = UnityEngine.Color.green;
        debugTextE.color = UnityEngine.Color.green;
        debugTextW.color = UnityEngine.Color.green;
    }

    IEnumerator GetRequest(string uri)
    {
        using (UnityWebRequest webRequest = UnityWebRequest.Get(uri))
        {
            webRequest.timeout = 1;
            yield return webRequest.SendWebRequest();

            if (webRequest.result == UnityWebRequest.Result.Success)
            {
                result = webRequest.downloadHandler.text;
                rightGripRotationSamples[gripIdx] = float.Parse(result.Split(',')[0]);
                leftGripRotationSamples[gripIdx] = float.Parse(result.Split(',')[1]);
                gripIdx = (gripIdx + 1) % SAMPLE_COUNT;
                rightGripRotation = getGripAvg(rightGripRotationSamples);
                leftGripRotation = getGripAvg(leftGripRotationSamples);
                showSuccess();
            }
            else
            {
                showError(webRequest.error);
            }
    }
    }
}
