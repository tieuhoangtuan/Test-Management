const error = document.getElementById("span-error");

if (error.textContent.trim() !== "") {
    error.classList.add("message-error"); // Thêm class highlight nếu có dữ liệu
} else {
    error.classList.remove("message-error"); // Xóa class highlight nếu không có dữ liệu
}

const success = document.getElementById("span-success");

if (success.textContent.trim() !== "") {
    success.classList.add("message-success");
} else {
    success.classList.remove("message-success");
}