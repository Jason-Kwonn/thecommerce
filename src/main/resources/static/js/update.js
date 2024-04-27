document.addEventListener("DOMContentLoaded", function() {
    const updateForm = document.getElementById('updateForm');
    const memberId = document.getElementById('memberId');
    const passwordInput = document.getElementById('password');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const mailAddressInput = document.getElementById('mailAddress');

    // Fetch existing member data
    const id = localStorage.getItem('id');
    fetch(`/api/user/getMember/${id}`)
        .then(response => response.json())
        .then(data => {
            memberId.value = data.memberId;
            document.getElementById('nickName').value = data.nickName;
            document.getElementById('name').value = data.name;
            phoneNumberInput.value = data.phoneNumber;
            mailAddressInput.value = data.mailAddress;
        })
        .catch(error => console.error('Failed to load member data:', error));

    function checkPassword() {
        const regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])([A-Za-z\d!@#$%^&*]{1,20})$/;
        if (!regex.test(passwordInput.value)) {
            displayMessage(passwordInput, "영문, 숫자, 특수문자를 조합한 비밀번호 최대 20자리를 입력해 주세요.", false);
        } else {
            displayMessage(passwordInput, "사용 가능", true);
        }
    }

    function formatPhoneNumber() {
        let numbers = phoneNumberInput.value.replace(/\D/g, '');
        if (numbers.length > 11) {
            numbers = numbers.substring(0, 11);
        }
        phoneNumberInput.value = numbers.replace(/(\d{3})(\d{4})(\d{4})/, '010-$2-$3');
    }

    function autoCompleteEmail() {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 기본 이메일 형식 검증 정규식
        if (!emailRegex.test(mailAddressInput.value)) {
            displayMessage(mailAddressInput, "이메일 형식이 올바르지 않습니다.", false);
        } else {
            displayMessage(mailAddressInput, "사용 가능", true); // 올바른 형식일 때는 메시지를 비워줍니다.
        }
    }

    function displayMessage(input, message, isValid) {
        const messageElement = input.parentNode.querySelector('.message');
        messageElement.textContent = message;
        messageElement.style.color = isValid ? 'green' : 'red';
    }

    passwordInput.addEventListener('blur', checkPassword);
    phoneNumberInput.addEventListener('input', formatPhoneNumber);
    mailAddressInput.addEventListener('input', autoCompleteEmail);

    updateForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = {
            password: passwordInput.value,
            nickName: document.getElementById('nickName').value,
            name: document.getElementById('name').value,
            phoneNumber: phoneNumberInput.value.replace(/\D/g, ''),
            mailAddress: mailAddressInput.value
        };

        fetch(`/api/user/updateMember?memberId=${memberId.value}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (response.ok) {
                    alert('회원 정보가 수정되었습니다!');
                    window.location.href = '/home';
                } else {
                    throw new Error('회원 정보 수정에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('회원 정보 수정에 실패했습니다.');
            });
    });
});
