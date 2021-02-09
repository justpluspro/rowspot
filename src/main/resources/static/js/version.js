window.onload = function() {
    let rootPath = '/';
    axios({
        url: rootPath + 'api/version',
        headers: {
            'Content-Type': 'application/json;charset=utf8',
        },
        method: 'GET',
    }).then(res =>{
        document.getElementById('version').innerText = res.data.version;
        document.getElementById('build-time').innerText = res.data.buildTime;
    }).catch(error => {
        document.getElementById('version').innerText = 'unknown';
        document.getElementById('build-time').innerText = '1970-07-01 00:00:00';
    });
}
