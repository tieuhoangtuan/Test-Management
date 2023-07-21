const dateStart = document.getElementById("start-date")
const dateEnd = document.getElementById("end-date")
const mileId = document.getElementById("mileId")
function loadMilestone(milestoneId) {
    fetch('/milestones/get/' + milestoneId)
        .then(function(response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error: ' + response.status);
            }
        })
        .then(function(data) {
            // console.log(data);
            updateStartForm(data, milestoneId);
        })
        .catch(function(error) {
            console.error(error);
        });

    function updateStartForm(data, milestoneId) {
        dateStart.value = data.startDate;
        dateEnd.value = data.endDate;
        mileId.value = milestoneId;
    }
}


function openStartForm(id){
    const rawMileId = id;
    const regex = /-(\d+)/;
    const match = rawMileId.match(regex);
    const milestoneId = match[1];
    loadMilestone(milestoneId);
    document.getElementById("start-milestone-form").style.display = "block";
    document.getElementById("ui-overlay").style.display = "block";
}

function closeStartForm() {
    document.getElementById("start-milestone-form").style.display = "none";
    document.getElementById("ui-overlay").style.display = "none";
}
