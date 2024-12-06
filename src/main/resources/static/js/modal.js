var modal = document.getElementById("employerModal");
var btn = document.getElementById("employerButton");
var span = document.getElementsByClassName("close")[0];

btn.onclick = function () {
    modal.style.display = "block";
}

span.onclick = function () {
    modal.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function updateCompanyDetails() {
    const companySelect = document.getElementById("companySelect");
    const phoneNumber = document.getElementById("phoneNumber");
    const email = document.getElementById("email");
    const website = document.getElementById("website");

    // Lấy option được chọn
    const selectedOption = companySelect.options[companySelect.selectedIndex];

    // Lấy dữ liệu từ các thuộc tính data
    phoneNumber.value = selectedOption.getAttribute("data-phone") || "";
    email.value = selectedOption.getAttribute("data-email") || "";
    website.value = selectedOption.getAttribute("data-website") || "";

}
// Kiểm tra xem có thông báo hay không và hiển thị modal
document.addEventListener("DOMContentLoaded", function() {
    var message = /*[[${message}]]*/ 'Thông báo thành công!'; // Thay bằng Thymeleaf để lấy giá trị từ server nếu có
    var notificationContainer = document.getElementById('notification');
    var okButton = document.getElementById('okButton');

    if (message && message.trim() !== '') {
        // Hiển thị thông báo nếu có message
        notificationContainer.style.display = 'flex';
        notificationContainer.querySelector('span').textContent = message; // Đặt nội dung thông báo
    }

    // Lắng nghe sự kiện nhấn nút "OK" để ẩn thông báo
    okButton.addEventListener('click', function() {
        notificationContainer.style.display = 'none';
    });
});
