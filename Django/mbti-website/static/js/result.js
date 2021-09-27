const copyBtn = document.querySelector('.copy_btn');
const facebookShare = document.querySelector('.facebook_share');
const kakaoShare = document.querySelector('.kakao_share');
Kakao.init('your api key');

$(function () {
  let url = window.location.href;
  let img = $('.result_img img').attr('src');

  $("meta[property='og\\:url']").attr('content', url);
  $("meta[property='og\\:title']").attr('content', '대학 팀프로젝트 유형을 확인해보세요!!');
  $("meta[property='og\\:description']").attr('content', '대학 팀프로젝트 진행에 있어 역할분담에 활용해보세요!');
});

function shareKakao() {
  let result_url = window.location.href;
  Kakao.Link.sendDefault({
    objectType: 'feed',
    content: {
      title: '나의 팀프로젝트 유형은?',
      description: '나의 팀프로젝트 유형을 알아보자!!',
      imageUrl:
        window.location.host + '/img/img_logo.png',
      link: {
        mobileWebUrl: 'https://developers.kakao.com',
        androidExecParams: 'test',
      },
    },
    social: {
      likeCount: 10,
      commentCount: 20,
      sharedCount: 30,
    },
    buttons: [
      {
        title: '결과 보러가기',
        link: {
          webUrl: result_url,
          mobileWebUrl: result_url,
        },
      },
      {
        title: '테스트 하러가기',
        link: {
          mobileWebUrl: 'https://developers.kakao.com',
        },
      },
    ]
  });
}


function copy_url() {
  let tmp = document.createElement('input');
  let url = location.href;

  document.body.appendChild(tmp);
  tmp.value = url;
  tmp.select();
  document.execCommand("copy");
  document.body.removeChild(tmp);
  alert("URL이 복사되었습니다.");
}

function share_facebook() {
  let url = window.location.href;
  let facebook = 'https://www.facebook.com/sharer/sharer.php?kid_directed_site=0&sdk=joey&u=';
  let link = facebook + encodeURIComponent(url);
  window.open(link, "_blank", "width=600, height=400");
}

copyBtn.addEventListener('click', copy_url);
facebookShare.addEventListener('click', share_facebook);
kakaoShare.addEventListener('click', shareKakao);