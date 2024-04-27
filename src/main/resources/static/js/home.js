document.addEventListener("DOMContentLoaded", function() {
    const membersList = document.getElementById('membersList');
    const prevGroup = document.getElementById('prevGroup');
    const nextGroup = document.getElementById('nextGroup');
    const pageNumbers = document.getElementById('pageNumbers');
    let currentPage = 1;
    let totalPages = 10; // 서버에서 총 페이지 수를 가져와야 함
    const pagesPerGroup = 5; // 한 그룹당 페이지 수

    function loadMembers(page) {
        fetch(`/api/user/list?page=${page - 1}`)
            .then(response => response.json())
            .then(data => {
                membersList.innerHTML = '';
                data.forEach(member => {
                    const formattedDate = member.regDate.replace('T', ' ').slice(0, 16);
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${member.id}</td>
                        <td>${member.memberId}</td>
                        <td>${member.nickName}</td>
                        <td>${member.name}</td>
                        <td>${member.phoneNumber}</td>
                        <td>${member.mailAddress}</td>
                        <td>${formattedDate}</td>
                        <td>${member.timeAgo}</td>
                    `;
                    if (member.id === Number(localStorage.getItem('id'))) {
                        row.style.cursor = 'pointer';
                        row.title = '클릭하여 수정';
                        row.style.color = 'red';
                        row.addEventListener('click', () => {
                            window.location.href = '/auth/update'; // 수정 페이지로 이동
                        });
                    }
                    membersList.appendChild(row);
                });
                currentPage = page; // 현재 페이지 업데이트
                updatePagination(currentPage);
            })
            .catch(error => console.error('Failed to fetch data:', error));
    }

    function updatePagination(current) {
        pageNumbers.innerHTML = ''; // 페이지 번호 초기화
        let startPage = Math.floor((current - 1) / pagesPerGroup) * pagesPerGroup + 1;
        let endPage = Math.min(startPage + pagesPerGroup - 1, totalPages);

        for (let i = startPage; i <= endPage; i++) {
            const pageNumber = document.createElement('button');
            pageNumber.textContent = i;
            pageNumber.onclick = () => loadMembers(i);
            pageNumbers.appendChild(pageNumber);
        }

        // 이전/다음 그룹 버튼 상태 업데이트
        prevGroup.disabled = startPage === 1;
        nextGroup.disabled = endPage === totalPages;
    }

    prevGroup.addEventListener('click', () => {
        let newPage = currentPage - pagesPerGroup;
        if (newPage >= 1) loadMembers(newPage);
    });

    nextGroup.addEventListener('click', () => {
        let newPage = currentPage + pagesPerGroup;
        if (newPage <= totalPages) loadMembers(newPage);
    });

    loadMembers(currentPage); // 초기 페이지 로딩
});
