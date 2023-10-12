Chart.defaults.global.defaultFontColor = '#FFF';
const values = JSON.parse(document.querySelector("#table").dataset.data);
const colors = ['#0003ff', '#11ff89', '#c73975', '#00987b',
    '#d5a60d', '#bd00eb']

const chart = new Chart('chart', {

    type: 'line',
});
document.querySelectorAll("[data-y-axis-id]").forEach(checkbox => checkbox.addEventListener("change", function (e) {
    e.preventDefault();
    const checkedDatasets = [];
    document.querySelectorAll("[data-y-axis-id]").forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedDatasets.push({
                data: values[checkbox.dataset.yAxisId],
                borderColor: colors[checkbox.dataset.yAxisId % colors.length],
                backgroundColor: 'rgba(155,208,245,0)',
            });
        }
    });
    chart.data.datasets = checkedDatasets;
    chart.update();
}));

document.querySelectorAll("[data-x-axis-id]").forEach(radio => radio.addEventListener("change", function (e) {
    e.preventDefault();
    const selectedLabel = this.dataset.xAxisId
    chart.data.labels = values[selectedLabel];


    document.querySelectorAll("[data-y-axis-id]").forEach(function (checkbox) {
        const selectedData = checkbox.dataset.yAxisId;
        checkbox.disabled = (selectedLabel === selectedData);
        if (checkbox.disabled) {
            checkbox.checked = false;
            checkbox.dispatchEvent(new Event("change"));
        }
    });

    chart.update();
}));

