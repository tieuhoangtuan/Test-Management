const form = document.getElementById("result_form");
const addResult = document.getElementById("add_form");
const cancelForm = document.getElementById("cancel_form");

addResult.addEventListener('click', () => {
    form.style.top = "0";
})

cancelForm.addEventListener('click', () => {
    form.style.top = "-10000rem"
})