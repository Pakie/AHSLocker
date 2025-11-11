/*
Add this container to your admin dashboard HTML (e.g. `src/main/resources/templates/admin_dashboard.html`):
<div id="availability-widget" class="card"></div>

Include the script (served from `src/main/resources/static/js/admin_dashboard_availability.js`):
<script src="/js/admin_dashboard_availability.js" defer></script>
*/

(async function () {
    const currentYear = new Date().getFullYear();
    const url = `/api/admin/reports/availability?year=${currentYear}`;
    const containerId = 'availability-widget';

    function createContainerIfMissing() {
        let container = document.getElementById(containerId);
        if (!container) {
            const parent = document.getElementById('admin-dashboard') || document.body;
            container = document.createElement('div');
            container.id = containerId;
            parent.prepend(container);
        }
        return container;
    }

    function renderError(container, message) {
        container.innerHTML = `<h3>Availability (${currentYear})</h3><div class="error">Error: ${message}</div>`;
    }

    function renderReport(container, data) {
        // Basic header
        const title = `<h3>Availability — ${currentYear}</h3>`;

        // If data is an object, render key/value table. Otherwise stringify.
        let body = '';
        if (data && typeof data === 'object' && !Array.isArray(data)) {
            const rows = Object.entries(data)
                .map(([k, v]) => {
                    // Format nested objects / arrays as JSON string
                    const value = (v === null || v === undefined) ? '' :
                        (typeof v === 'object' ? `<pre style="margin:0">${JSON.stringify(v, null, 2)}</pre>` : `${v}`);
                    return `<tr><th style="text-align:left;padding-right:8px">${escapeHtml(k)}</th><td>${value}</td></tr>`;
                })
                .join('');
            body = `<table style="border-collapse:collapse; width:100%;">${rows}</table>`;
        } else {
            body = `<pre>${escapeHtml(JSON.stringify(data, null, 2))}</pre>`;
        }

        container.innerHTML = `<div class="availability-report">${title}${body}</div>`;
    }

    function escapeHtml(str) {
        return String(str)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }

    const container = createContainerIfMissing();

    try {
        const resp = await fetch(url, { method: 'GET', credentials: 'same-origin', headers: { 'Accept': 'application/json' } });
        if (!resp.ok) {
            const text = await resp.text().catch(() => resp.statusText);
            renderError(container, `Server returned ${resp.status}: ${escapeHtml(text)}`);
            return;
        }
        const json = await resp.json();
        renderReport(container, json);
    } catch (err) {
        renderError(container, err.message || String(err));
    }
})();
