function afterClick() {

    const x = document.getElementsByTagName("input")[0].value;

    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://api.github.com/users/' + x, true);

    xhr.onload = function () {

        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200) {
                const data = xhr.responseText;
                const obj = jQuery.parseJSON(data);

                // pulling user data
                const image_url = obj["avatar_url"];
                const name = obj["login"];
                const id = obj["id"];
                const followers = obj["followers"];
                const following = obj["following"];
                const repos = obj["public_repos"];
                const url = obj["html_url"];

                 

                $("#image").css("background-image", "url(" + image_url + ")");
                document.getElementById('field1').innerHTML = 'Name         :  '+ name;
                document.getElementById('field2').innerHTML = 'Id           :  '+ id;
                document.getElementById('field3').innerHTML = 'Public repos :  '+ repos;
                document.getElementById('field4').innerHTML = 'Followers    :  '+ followers;
                document.getElementById('field5').innerHTML = 'Following    :  '+ following;
                document.getElementById('field6').innerHTML = 'URL          :  '+ url;
            }
        }
    };
    xhr.send(null);
}
