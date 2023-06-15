// For OS
let os;
if (navigator.appVersion.indexOf("Win") !== -1) {
    os = "Window OS"
} else if (navigator.appVersion.indexOf("Mac") !== -1) {
    os = "MacOs"
} else if (navigator.appVersion.indexOf("Linux") !== -1) {
    os = "Linux OS"
} else if (navigator.appVersion.indexOf("X11") !== -1) {
    os = "Unix OS"
} else {
    os = "Unable to find OS Name"
}

// For Browser Name
let browser;
if (navigator.userAgent.indexOf('Chrome') !== -1) {
    browser = "Chrome"
} else if (navigator.userAgent.indexOf('Firefox') !== -1) {
    browser = "Firefox"
} else if (navigator.userAgent.indexOf('Edge') !== -1) {
    browser = "Edge"
} else if (navigator.userAgent.indexOf('Safari')) {
    browser = "Safari"
} else {
    browser = "Unable to find Browser Name"
}


// Calling api for Geolocation and storing browser info and os info
function ipLookUp() {
    fetch('http://ip-api.com/json')
        .then((response) => response.json())
        .then((data) => {
            let userInfo = {
                osName: os,
                browserName: browser,
                country: data.country,
                regionName: data.regionName,
                city: data.city,
                lat: data.lat,
                lon: data.lon,
                userName: '',
                loginTime: '',
                timeSpend: ''
            }
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'http://localhost:8080/store');
            xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
            xhr.onload = function () {
                if (xhr.status === 200) {
                    console.log(xhr.responseText)
                } else {
                    console.log('error: ' + xhr.status)
                }
            };
            xhr.send(JSON.stringify(userInfo));
        })
}
ipLookUp()


