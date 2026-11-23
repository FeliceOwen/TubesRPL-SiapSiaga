(function () {
  "use strict";

  var LoginModule = (function () {
    var form = null;
    var alertBox = null;
    var alertMessage = null;
    var passwordInput = null;
    var passwordToggle = null;

    var INVALID_CREDENTIALS_MESSAGE = "NIK atau Password salah";

    function isValidNik(value) {
      return /^\d{16}$/.test(value.trim());
    }

    function isValidPassword(value) {
      return value.length > 0;
    }

    function showAlert(message, type) {
      alertMessage.textContent = message;
      alertBox.classList.remove("alert-error", "alert-success");
      alertBox.classList.add(
        type === "success" ? "alert-success" : "alert-error",
      );
      alertBox.hidden = false;
    }

    function hideAlert() {
      alertBox.hidden = true;
      alertMessage.textContent = "";
    }

    function initPasswordToggle() {
      passwordToggle.addEventListener("click", function () {
        var isVisible = passwordInput.type === "text";
        passwordInput.type = isVisible ? "password" : "text";
        passwordToggle.setAttribute("aria-pressed", String(!isVisible));
        passwordToggle.setAttribute(
          "aria-label",
          isVisible ? "Tampilkan password" : "Sembunyikan password",
        );
      });
    }

    function setSubmitLoading(isLoading) {
      var submitButton = form.querySelector(".submit");
      submitButton.disabled = isLoading;
      submitButton.textContent = isLoading ? "Memproses..." : "Masuk";
    }

    function initFormSubmit() {
      form.addEventListener("submit", function (event) {
        hideAlert();

        var nikInput = document.getElementById("nik");
        var nikValid = isValidNik(nikInput.value);
        var passwordValid = isValidPassword(passwordInput.value);

        if (!nikValid || !passwordValid) {
          event.preventDefault();
          showAlert(INVALID_CREDENTIALS_MESSAGE, "error");
          (nikValid ? passwordInput : nikInput).focus();
          return;
        }

        setSubmitLoading(true);
      });
    }

    function init() {
      form = document.getElementById("login-form");
      alertBox = document.getElementById("login-alert");
      alertMessage = document.getElementById("login-alert-message");
      passwordInput = document.getElementById("password");
      passwordToggle = document.getElementById("password-toggle");

      if (!form || !alertBox || !passwordInput || !passwordToggle) return;

      var serverError = alertBox.getAttribute("data-error");
      if (serverError) {
        showAlert(serverError, "error");
      }

      initPasswordToggle();
      initFormSubmit();
    }

    return { init: init };
  })();

  var RegisterModule = (function () {
    var form = null;
    var alertBox = null;
    var alertMessage = null;
    var passwordInput = null;
    var passwordToggle = null;

    function isValidNik(value) {
      return /^\d{16}$/.test(value.trim());
    }

    function isValidNama(value) {
      return value.trim().length >= 3;
    }

    function isValidPassword(value) {
      return value.length >= 8 && value.length <= 10;
    }

    function showAlert(message, type) {
      alertMessage.textContent = message;
      alertBox.classList.remove("alert-error", "alert-success");
      alertBox.classList.add(
        type === "success" ? "alert-success" : "alert-error",
      );
      alertBox.hidden = false;
    }

    function hideAlert() {
      alertBox.hidden = true;
      alertMessage.textContent = "";
    }

    function initPasswordToggle() {
      passwordToggle.addEventListener("click", function () {
        var isVisible = passwordInput.type === "text";
        passwordInput.type = isVisible ? "password" : "text";
        passwordToggle.setAttribute("aria-pressed", String(!isVisible));
        passwordToggle.setAttribute(
          "aria-label",
          isVisible ? "Tampilkan password" : "Sembunyikan password",
        );
      });
    }

    function setSubmitLoading(isLoading) {
      var submitButton = form.querySelector(".submit");
      submitButton.disabled = isLoading;
      submitButton.textContent = isLoading ? "Memproses..." : "Buat Akun";
    }

    function initFormSubmit() {
      form.addEventListener("submit", function (event) {
        hideAlert();

        var nikInput = document.getElementById("nik");
        var namaInput = document.getElementById("nama");
        var nikValid = isValidNik(nikInput.value);
        var namaValid = isValidNama(namaInput.value);
        var passwordValid = isValidPassword(passwordInput.value);

        if (!nikValid || !namaValid || !passwordValid) {
          event.preventDefault();
          if (!nikValid) {
            showAlert("NIK harus 16 digit angka", "error");
            nikInput.focus();
          } else if (!namaValid) {
            showAlert("Nama lengkap minimal 3 karakter", "error");
            namaInput.focus();
          } else {
            showAlert("Password harus 8-10 karakter", "error");
            passwordInput.focus();
          }
          return;
        }

        setSubmitLoading(true);
      });
    }

    function init() {
      form = document.getElementById("register-form");
      alertBox = document.getElementById("register-alert");
      alertMessage = document.getElementById("register-alert-message");
      passwordInput = document.getElementById("password");
      passwordToggle = document.getElementById("password-toggle");

      if (!form || !alertBox || !passwordInput || !passwordToggle) return;

      var serverError = alertBox.getAttribute("data-error");
      if (serverError) {
        showAlert(serverError, "error");
      }

      initPasswordToggle();
      initFormSubmit();
    }

    return { init: init };
  })();

  document.addEventListener("DOMContentLoaded", function () {
    LoginModule.init();
    RegisterModule.init();
  });
})();
