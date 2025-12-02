const hours = document.getElementById("hours");
        const minutes = document.getElementById("minutes");
        const seconds = document.getElementById("seconds");
        const dateDisplay = document.getElementById("date-display");

        function updateClock(){
            const time = new Date();
            let hoursNow = time.getHours();
            let minutesNow = time.getMinutes();
            let secondsNow = time.getSeconds();

            hours.textContent = formatDate(hoursNow);
            minutes.textContent = formatDate(minutesNow);
            seconds.textContent = formatDate(secondsNow);

            const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
            dateDisplay.textContent = time.toLocaleDateString('ru-RU', options);
        }

        function formatDate(time) {
            return time.toString().padStart(2, '0');
        }

        updateClock();
        setInterval(updateClock, 10000);