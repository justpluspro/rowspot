const rootPath = '/';

document.getElementById('loginBtn').addEventListener('click', function() {
    let emailElement = document.getElementById('email');
    let passwordElement = document.getElementById('password');
    let emailHelp = document.getElementById('emailHelp')
    let passwordHelp = document.getElementById('passwordHelp')
    let password = passwordElement.value;
    let email = emailElement.value;

    if(email === null || email.length === 0 || email === ""){
        emailElement.classList.add("is-danger");
        emailHelp.style.display = 'block';
        return;
    }

    if(password === null || password.length === 0 || password === ""){
        passwordElement.classList.add("is-danger");
        passwordHelp.style.display = 'block';
        return;
    }

    let loginBtn = this;

    loginBtn.classList.add('is-loading');
    loginBtn.disabled = 'true';

    axios({
        method: 'POST',
        url: rootPath + 'api/token',
        header: {
            'Content-Type': 'application/json;charset=utf8',
        },
        data: {
            "email": email,
            "password": password
        }
    }).then((response) => {
        loginBtn.classList.remove('is-loading');
        loginBtn.disabled = '';
        let redirect = getQueryVariable("redirect");
        console.log("redirect:" + decodeURI(redirect));
        if(redirect !== null && redirect !== '' && redirect.trim().length !== 0) {
            loginBtn.classList.remove('is-loading');
            loginBtn.disabled = '';
            window.location.href = decodeURIComponent(redirect);
        } else {
            window.location.href = rootPath;
            loginBtn.classList.remove('is-loading');
            loginBtn.disabled = '';
        }
    }).catch((error) => {
        let data = error.response.data;
        let status = error.response.status;
        let msg = data.errors.msg;
        let code = data.errors.code;

        let loginTipElement = document.getElementById('loginTips');
        loginTipElement.style.display = 'block';
        loginTipElement.innerText = msg;
        loginBtn.classList.remove('is-loading');
        loginBtn.disabled = '';
        passwordElement.value = '';
        emailHelp.style.display = 'none';
        emailElement.classList.remove('is-danger');
        passwordHelp.style.display = 'none';
        passwordElement.classList.remove('is-danger');
    });
})

function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length;i++) {
        let pair = vars[i].split("=");
        if(pair[0] === variable){
            return pair[1];
        }
    }
    return "";
}
