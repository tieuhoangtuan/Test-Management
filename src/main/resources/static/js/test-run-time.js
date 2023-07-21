let time = new Date();
let interValId = null;
let isIncrementing = false;

time.setHours(0, 0, 0, 0);

function updateTime() {
    let hours = time.getHours().toString().padStart(2, "0");
    let minutes = time.getMinutes().toString().padStart(2, "0");
    document.getElementById("current-time").innerHTML = hours + ":" + minutes;
}

function startIncrementing() {
    isIncrementing = true;
    interValId = setInterval(function () {
        time.setMinutes(time.getMinutes() + 1);
        updateTime();
    }, 1000);
    document.getElementById("time-is-text").style.display = "inline-block";
    document.getElementById("current-time").style.display = "inline-block";
    document.getElementById("start-button").style.display = "none";
    document.getElementById("pause-button").style.display = "inline-block";
    document.getElementById("stop-button").style.display = "inline-block";
}

function pauseIncrementing() {
    isIncrementing = false;
    clearInterval(interValId);
    document.getElementById("pause-button").style.display = "none";
    document.getElementById("start-button").style.display = "inline-block";
}

function stopIncrementing() {
    isIncrementing = false;
    clearInterval(interValId);
    document.getElementById("pause-button").style.display = "none";
    document.getElementById("stop-button").style.display = "none";
    document.getElementById("start-button").style.display = "inline-block";
    let hours = time.getHours().toString();
    let minutes = time.getMinutes().toString();
    let timeStrings = `${hours === "0" ? "" : hours + "m "}${minutes}s`;
    document.getElementById("elapsed").value = timeStrings;
    document.getElementById("result_form").style.top = "0";
}