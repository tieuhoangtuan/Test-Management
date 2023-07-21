let time = new Date();
let interValId = null;
let isIncrementing = false;

time.setHours(0, 0, 0, 0);

function updateTime() {
    let hours = time.getHours().toString();
    let minutes = time.getMinutes().toString();
    let timeStrings = `${hours === "0" ? "" : hours + "m "}${minutes==="0"? "": minutes+"s"}`;
    document.getElementById("elapsed").value = timeStrings;
}
function startTimer(){
    isIncrementing = true;
    interValId = setInterval(function () {
        time.setMinutes(time.getMinutes() + 1);
        updateTime();
    }, 1000);
}
function startIncrementing() {
    time.setHours(0, 0, 0, 0);
    updateTime();
    startTimer();
    document.getElementById("startBtn").style.display = "none";
    document.getElementById("pauseBtn").style.display = "inline-block";
    document.getElementById("stopBtn").style.display = "inline-block";
}

function resumeIncrementing(){
    startTimer();
    document.getElementById("resumeBtn").style.display = "none";
    document.getElementById("pauseBtn").style.display = "inline-block";
    document.getElementById("stopBtn").style.display = "inline-block";
}
function pauseIncrementing() {
    isIncrementing = false;
    clearInterval(interValId);
    document.getElementById("pauseBtn").style.display = "none";
    document.getElementById("resumeBtn").style.display = "inline-block";
    document.getElementById("stopBtn").style.display = "inline-block";
}

function stopIncrementing() {
    isIncrementing = false;
    clearInterval(interValId);
    document.getElementById("pauseBtn").style.display = "none";
    document.getElementById("stopBtn").style.display = "none";
    document.getElementById("startBtn").style.display = "inline-block";
    updateTime();
}

function openAddResultForm(id){
    const rawTestRunUserId = id;
    const regex = /-(\d+)/;
    const match = rawTestRunUserId.match(regex);
    const testRunUserId = match[1];
    const statusSelect = document.getElementById("statusForm");
    statusSelect.value = "Passed";
    document.getElementById("statusForm").click();
    document.getElementsByClassName("add-result-form")[0].style.display = "block";
    document.getElementById("ui-overlay").style.display = "block";
    document.getElementById("testRunUserId").value = testRunUserId;
}

function closeAddResultForm(){
    document.getElementsByClassName("add-result-form")[0].style.display = "none";
    document.getElementById("ui-overlay").style.display = "none";
}
function changColorByStatus(value){
    const headerColor = document.getElementById("header-form-test-result");
    if(value === "Passed"){
        headerColor.style.backgroundColor ="rgb(43, 117, 55)";
    } else if(value === "Blocked"){
        headerColor.style.backgroundColor ="rgb(60, 60, 60)";
    } else if(value === "Retest"){
        headerColor.style.backgroundColor ="rgb(157, 123, 7)";
    } else if(value === "Failed"){
        headerColor.style.backgroundColor ="rgb(143, 7, 49)";
    }
}

function openCommentForm(){
    document.getElementById("add-comment-form").style.display="block";
    document.getElementById("ui-overlay").style.display = "block";
}

function closeCommentForm(){
    document.getElementById("add-comment-form").style.display="none";
    document.getElementById("ui-overlay").style.display = "none";
}


