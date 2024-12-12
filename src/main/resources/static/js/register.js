function redirectToRole(role) {
    // Kiểm tra xem người dùng đã chọn "candidate" hay chưa

    // Nếu có lựa chọn, chuyển hướng
    if (role) {
        var selectedRole = role.value;
        window.location.href = "/register?role=" + selectedRole; // Chuyển đến link "/role?candidate" hoặc "/role?company"
    } else {
        alert("Please select a role.");
    }
}
// Check if the user has visited before
if (!localStorage.getItem('hasVisited')) {
    // If not, show the modal
    var button_modal = document.getElementById("button-active-modal");
    button_modal.click()
    localStorage.setItem("hasVisited",true)
}