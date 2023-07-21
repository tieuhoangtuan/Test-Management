const form = document.getElementById("choose_form");
const showForm = document.getElementById("show_form");
const cancelForm = document.getElementById("cancel_form")

showForm.addEventListener('click', () => {
    form.style.top = "0";
})

cancelForm.addEventListener('click', () => {
    form.style.top = "-10000rem";
})
