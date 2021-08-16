using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameUI : MonoBehaviour
{
    public GameObject gameWinUI;
    public GameObject gameLoseUI;
    bool gameIsOver;

    void Start()
    {
        Guard.OnGuardSpottedPlayer += ShowGameLoseUI;
        Player.OnReachEndOfLevel += ShowGameWinUI;
    }

    void Update()
    {
        if (gameIsOver)
        {
            if (Input.GetKeyDown(KeyCode.Space))
            {
                SceneManager.LoadScene(0);
            }
        }
    }

    void ShowGameWinUI()
    {
        OnGameOver(gameWinUI);
    }

    void ShowGameLoseUI()
    {
        OnGameOver(gameLoseUI);
    }

    void OnGameOver(GameObject gameObject)
    {
        gameObject.SetActive(true);
        gameIsOver = true;
        Guard.OnGuardSpottedPlayer -= ShowGameLoseUI;
        Player.OnReachEndOfLevel -= ShowGameWinUI;
    }
}
