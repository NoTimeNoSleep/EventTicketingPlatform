(function () {
    function pad(number) {
        return number < 10 ? '0' + number : String(number);
    }

    function formatTimeLeft(totalSeconds) {
        var minutes = Math.floor(totalSeconds / 60);
        var seconds = totalSeconds % 60;
        return pad(minutes) + ':' + pad(seconds);
    }

    function getCountdownElements() {
        return document.querySelectorAll('[data-cart-expiry]');
    }

    function updateCountdown() {
        var countdownElements = getCountdownElements();
        var hasExpired = false;

        if (!countdownElements.length) {
            return;
        }

        for (var index = 0; index < countdownElements.length; index++) {
            var countdownElement = countdownElements[index];
            var expiryMillis = parseInt(countdownElement.getAttribute('data-cart-expiry'), 10);

            if (!expiryMillis || isNaN(expiryMillis)) {
                continue;
            }

            var secondsLeft = Math.floor((expiryMillis - Date.now()) / 1000);

            if (secondsLeft <= 60) {
                countdownElement.classList.add('cart-timer-warning');
            } else {
                countdownElement.classList.remove('cart-timer-warning');
            }

            if (secondsLeft <= 0) {
                countdownElement.textContent = '00:00';
                hasExpired = true;
                continue;
            }

            countdownElement.textContent = formatTimeLeft(secondsLeft);
        }

        if (hasExpired) {
            window.location.reload();
        }
    }

    function startCountdown() {
        updateCountdown();

        if (getCountdownElements().length) {
            window.setInterval(updateCountdown, 1000);
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', startCountdown);
    } else {
        startCountdown();
    }
}());