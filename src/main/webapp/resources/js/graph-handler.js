function getCurrentR() {
                    // Получаем R из скрытого поля
                    const rInput = document.querySelector('[id*="current-r"]');
                    if (rInput && rInput.value) {
                        return parseFloat(rInput.value);
                    }
                    return 2.0; // значение по умолчанию
                }

                function convertToGraphX(pixelX) {
                    const centerX = 150;
                    const scale = 100;
                    const r = getCurrentR();
                    return ((pixelX - centerX) / scale * r).toFixed(2);
                }

                function convertToGraphY(pixelY) {
                    const centerY = 150;
                    const scale = 100;
                    const r = getCurrentR();
                    return ((centerY - pixelY) / scale * r).toFixed(2);
                }

                function handleGraphClick(event) {
                    const r = getCurrentR();
                    if (!r || r <= 0) {
                        showGraphError("Сначала установите радиус R!");
                        return;
                    }

                    const svg = document.getElementById('graph');
                    const rect = svg.getBoundingClientRect();
                    const x = event.clientX - rect.left;
                    const y = event.clientY - rect.top;

                    const graphX = convertToGraphX(x);
                    const graphY = convertToGraphY(y);

                    setFormValues(graphX, graphY);
                    submitForm();
                }

                function showGraphError(message) {
                    // Можно показать alert или добавить сообщение в интерфейс
                    alert(message);
                    // Или добавить кастомное сообщение на страницу
                    const errorDiv = document.getElementById('graph-error');
                    if (errorDiv) {
                        errorDiv.innerHTML = '<div class="errors">' + message + '</div>';
                        setTimeout(() => errorDiv.innerHTML = '', 3000);
                    }
                }