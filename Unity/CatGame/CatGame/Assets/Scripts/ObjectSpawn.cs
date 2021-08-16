using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObjectSpawn : MonoBehaviour
{
    public ObjectManager objectManager;

    public float spawnBetTimeMin;
    public float spawnBetTimeMax;

    float spawnTime;
    float nextSpawnTime;
    float timeAfterSpawn;
    float randomSpawnTime;

    int carCount;

    string[] carName;

    Vector2 poolPos = new Vector2(20, 0);

    void Start()
    {
        nextSpawnTime = 1f;
        carCount = 0;

        carName = new string[] { "car1", "car2", "car3", "car4", "car5", "car6", "car7", "car8", "car9"};
    }

    void Update()
    {
        timeAfterSpawn += Time.deltaTime;
        CarSpawn();
    }

    void CarSpawn()
    {
        if(timeAfterSpawn >= nextSpawnTime)
        {
            carCount++;

            int carSequence = carCount % carName.Length;
            GameObject carObject = objectManager.MakeObject(carName[carSequence]);

            spawnTime = Random.Range(spawnBetTimeMin, spawnBetTimeMax);
            nextSpawnTime = timeAfterSpawn + spawnTime;
        }
    }
}
