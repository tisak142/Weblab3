function getCurrentR() {
    const rInput = document.querySelector('[id*="current-r"]');
    if (rInput && rInput.value && rInput.value.trim() !== '') {
        const value = parseFloat(rInput.value);
        return !isNaN(value) ? value : null;
    }
    return null; // null если R не установлен
}
                function convertToGraphX(pixelX) {
                    const centerX = 150;
                    const scale = 100;
                    const r = getCurrentR();
                    return ((pixelX - centerX) / scale * r);
                }

                function convertToGraphY(pixelY) {
                    const centerY = 150;
                    const scale = 100;
                    const r = getCurrentR();
                    return ((centerY - pixelY) / scale * r);
                }

                function setFormValues(x, y) {
                    const xInput = document.querySelector('[id*="graph-x"]');
                    const yInput = document.querySelector('[id*="graph-y"]');

                    if (xInput && yInput) {
                        xInput.value = x;
                        yInput.value = y;
                    }
                }

                function submitForm() {
                    const submitButton = document.querySelector('[id*="graph-submit"]');
                    if (submitButton) {
                        submitButton.click();
                    }
                }

                function handleGraphClick(event) {
                    const r = getCurrentR();


                    const svg = document.getElementById('graph');
                    const rect = svg.getBoundingClientRect();
                    const x = event.clientX - rect.left;
                    const y = event.clientY - rect.top;

                    const graphX = convertToGraphX(x);
                    const graphY = convertToGraphY(y);

                    console.log("Graph click - X:", graphX, "Y:", graphY, "R:", r);

                    setFormValues(graphX, graphY);
                    submitForm();
                }

function makeNoiseWindows() {
    const sound = document.getElementById('windowsSound');
    if (sound) {
        sound.currentTime = 0;
        sound.play().catch(e => console.log("Ошибка воспроизведения:", e));
    }
}

function makeNoiseHit() {
    const sound = document.getElementById('hitSound');
    if (sound) {
        sound.currentTime = 0;
        sound.play().catch(e => console.log("Ошибка воспроизведения:", e));
    }
}

function makeNoiseNoHit() {
    const sound = document.getElementById('noHitSound');
    if (sound) {
        sound.currentTime = 0;
        sound.play().catch(e => console.log("Ошибка воспроизведения:", e));
    }
}

function makeNoiseValues() {
    const sound = document.getElementById('clearValuesSound');
    if (sound) {
        sound.currentTime = 0;
        sound.play().catch(e => console.log("Ошибка воспроизведения:", e));
    }
}

function handleCheckResult(data) {
    // data.status может быть: 'begin', 'complete', 'success'
    if (data.status === 'complete') {
        // Ждем 400ms чтобы DOM обновился
        setTimeout(() => {
            // 1. СНАЧАЛА ПРОВЕРЯЕМ УСТАНОВЛЕН ЛИ R
            const currentR = getCurrentR();
            if (!currentR || currentR <= 0) {
                console.log("R не установлен, звук не играем");
                return; // Выходим если R не установлен
            }

            // 2. Потом проверяем hidden поле
            const resultField = document.querySelector('[id*="lastHitResult"]');

            if (resultField && resultField.value !== '') {
                // Берем значение из поля
                const result = resultField.value === 'true';

                // Играем соответствующий звук
                if (result) {
                    makeNoiseHit();     // звук попадания
                } else {
                    makeNoiseNoHit();   // звук промаха
                }
            } else {
                console.log("Поле lastHitResult пустое (была ошибка валидации)");
            }
        }, 400);
    }
}

function handleResetButton() {
    makeNoiseValues();  // первая функция
    resetCounters();    // вторая функция (если нужна)
    return true;        // обязательно return true
}