/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/main/resources/templates/**/*.html"],
    theme: {
        extend: {
            colors: {
                brand: {
                    DEFAULT: '#b91c1c'
                }
            }
        },
    },
    plugins: [],
}