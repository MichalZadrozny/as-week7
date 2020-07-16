function goTo(value) {
    if (value === undefined) {
        location.href = location.protocol + '//' + location.host;
    } else {
        location.href = value;
    }
}