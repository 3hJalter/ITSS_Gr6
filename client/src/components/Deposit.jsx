import React from 'react'
import { useLocation } from 'react-router-dom'

function Deposit() {
  const location = useLocation();
  const barcode = location.state;

  return (
    <>
    <div>
      <div></div>
    </div>
    </>
  )
}

export default Deposit