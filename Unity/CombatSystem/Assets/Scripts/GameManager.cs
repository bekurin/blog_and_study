using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    #region Singleton
    public static GameManager instance;

    void Awake()
    {
        if(instance == null)
        {
            instance = this;
        }
        else
        {
            Destroy(gameObject);
        }
    }
    #endregion

    public Vector2 limitPoint1;
    public Vector2 limitPoint2;

    public GameObject gameWInUI;
    public GameObject gameLoseUI;

    public bool isOver = false;

    void Update()
    {
        if (isOver)
        {
            if (Input.GetButtonDown("Jump"))
            {
                SceneManager.LoadScene(0);
            }
        }    
    }

    public void GameOver(bool isWin)
    {
        if (isWin)
        {
            gameWInUI.SetActive(true);
            isOver = true;
        }
        else
        {
            gameLoseUI.SetActive(true);
            isOver = true;
        }
    }
}
