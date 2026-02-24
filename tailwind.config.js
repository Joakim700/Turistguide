/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/main/resources/templates/**/*.html", // all your Thymeleaf templates
        "./src/main/resources/static/**/*.js"       // any JS files using Tailwind
    ],
    theme: {
        extend: {},
    },
    plugins: [],
}