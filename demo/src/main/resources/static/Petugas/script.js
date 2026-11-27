document.addEventListener("DOMContentLoaded", function () {
    const filterSelect = document.getElementById("status_verifikasi");
    const cards = document.querySelectorAll(".report-card");
    const statusSaatIni = document.getElementById("statusSaatIni");

    if (filterSelect) {
        filterSelect.addEventListener("change", function () {
            const selectedStatus = this.value;
            const selectedOption = this.options[this.selectedIndex];
            const labelStatus = selectedOption ? selectedOption.textContent.trim() : "";

            // Filter kartu laporan (urutan DOM sudah rapi dari Controller)
            cards.forEach(card => {
                const cardStatus = card.getAttribute("data-status");
                if (!selectedStatus || selectedStatus === "semua" || cardStatus === selectedStatus) {
                    card.style.display = "block";
                } else {
                    card.style.display = "none";
                }
            });

            // Update teks "Status Saat Ini" jika elemennya ada di halaman
            if (statusSaatIni) {
                if (!selectedStatus || selectedStatus === "semua") {
                    statusSaatIni.textContent = statusSaatIni.dataset.initialText || "-";
                } else if (labelStatus) {
                    statusSaatIni.textContent = labelStatus;
                }
            }
        });
    }
});