const message = document.getElementById("span-message");

if(message.textContent.trim() !== "" ){
    if(message.innerHTML.includes("Invalid"))
        message.classList.add("error");
    else if(message.innerHTML.includes("already"))
        message.classList.add("error");
    else
        message.classList.add("success")
} else {
    message.classList.remove("success")
    message.classList.remove("error")
}