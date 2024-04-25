document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/user/list')
        .then(response => response.json())
        .then(data => {
            const membersList = document.getElementById('membersList');
            data.forEach(member => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${member.memberId}</td>
                    <td>${member.nickName}</td>
                    <td>${member.name}</td>
                    <td>${member.phoneNumber}</td>
                    <td>${member.mailAddress}</td>
                    <td>${new Date(member.regDate).toLocaleDateString()}</td>
                    <td>${member.timeAgo}</td>
                `;
                membersList.appendChild(row);
            });
        })
        .catch(error => console.error('Failed to fetch data:', error));
});
