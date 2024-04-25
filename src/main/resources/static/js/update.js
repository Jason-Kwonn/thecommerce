document.addEventListener("DOMContentLoaded", function() {
    const updateForm = document.getElementById('updateForm');
    const userId = localStorage.getItem('userId');  // 로컬 스토리지에서 사용자 ID를 가져옴

    fetch(`/api/user/${userId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('memberId').value = data.memberId;
            document.getElementById('nickName').value = data.nickName;
            document.getElementById('name').value = data.name;
            document.getElementById('phoneNumber').value = data.phoneNumber;
            document.getElementById('mailAddress').value = data.mailAddress;
        });

    updateForm.addEventListener('submit', function(event) {
        event.preventDefault();
        submitUpdateForm(userId);
    });
});

function submitUpdateForm(userId) {
    const formData = {
        memberId: document.getElementById('memberId').value,
        password: document.getElementById('password').value,
        nickName: document.getElementById('nickName').value,
        name: document.getElementById('name').value,
        phoneNumber: document.getElementById('phoneNumber').value,
        mailAddress: document.getElementById('mailAddress').value
    };

    fetch(`/api/user/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('회원 정보 수정 실패');
            }
            return response.json();
        })
        .then(data => {
            alert('회원 정보가 수정되었습니다!');
            window.location.href = '/home';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('회원 정보 수정에 실패했습니다.');
        });
}
