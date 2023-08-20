/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      boxShadow: {
        "3xl": "rgba(0, 0, 0, 0.35) 0px 5px 15px",
      },
      backgroundColor: {
        primary: "#4d5a76",
      },
      textColor: {
        primary: "#4d5a76",
      },
    },
  },
  plugins: [],
};
