using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    public static GameManager GM;
    public static int totalScore;

    public Text scoreText;

    public GameObject[] playerHealth;
    public int health;
    int maxHealth;

    public GameObject gameOverUI;
    public GameObject gameWinUI;
    bool isGameOver;

    void Awake()
    {
        if(GM == null)
        {
            GM = this;
        }
        else
        {
            Destroy(gameObject);
        }

        Time.timeScale = 1;
        isGameOver = false;
        maxHealth = health;
    }

    void Update()
    {
        if (isGameOver)
        {
            if (Input.GetButton("Jump"))
            {
                SceneManager.LoadScene(0);
            }
        }
    }

    public void HelathDown()
    {
        health--;
        for (int i = 0; i < maxHealth; i++)
        {
            playerHealth[i].SetActive(false);
        }

        for (int i = 0; i < health; i++)
        {
            playerHealth[i].SetActive(true);
        }

        if(health <= 0 && !isGameOver)
        {
            GameOver();
            Time.timeScale = 0;
            isGameOver = true;
        }
    }

    public void AddScore(int score)
    {
        totalScore += score;
        scoreText.text = "Score: " + totalScore;

        if(totalScore >= 2000 && !isGameOver)
        {
            GameWinUI();
            Time.timeScale = 0;
            isGameOver = true;
        }
    }

    public void GameOver()
    {
        gameOverUI.SetActive(true);
    }

    public void GameWinUI()
    {
        gameWinUI.SetActive(true);
    }
}
