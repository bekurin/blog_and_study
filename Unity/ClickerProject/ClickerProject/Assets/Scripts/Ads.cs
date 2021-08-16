using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Monetization;

public class Ads : MonoBehaviour
{
    public string placementId = "rewardedVideo";

#if UNITY_IOS
    private string gameId = "123467";
#elif UNITY_ANDROID
    private string gameId = "213545";
#elif UNITY_EDITOR
    private string gameId = "214956";
#endif

    void Start()
    {
        if (Monetization.isSupported)
        {
            Monetization.Initialize(gameId, true);
        }
    }

    public void ShowAd()
    {
        ShowAdCallbacks options = new ShowAdCallbacks();
        options.finishCallback = HandleShowResult;

        ShowAdPlacementContent ad = Monetization.GetPlacementContent(placementId) as ShowAdPlacementContent;

        ad.Show(options);
    }

    void HandleShowResult(ShowResult result)
    {
        if(result == ShowResult.Finished)
        {
            GameManager.money += 1000;
        }
        else if(result == ShowResult.Skipped)
        {

        }
        else
        {

        }
    }
}
