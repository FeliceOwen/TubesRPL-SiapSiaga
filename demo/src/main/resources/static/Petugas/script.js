document.addEventListener("DOMContentLoaded", function () {
    const filterSelect = document.getElementById("status_verifikasi");
    const cards = document.querySelectorAll(".report-card");

    if (filterSelect) {
        filterSelect.addEventListener("change", function () {
            const selectedStatus = this.value;

            cards.forEach(card => {
                const cardStatus = card.getAttribute("data-status");
                if (selectedStatus === "semua" || cardStatus === selectedStatus) {
                    card.style.display = "block";
                } else {
                    card.style.display = "none";
                }
            });
        });
    }
});