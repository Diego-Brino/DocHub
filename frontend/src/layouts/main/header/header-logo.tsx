import logo from "../../../assets/logo.svg";

function HeaderLogo() {
  return (
    <div className='flex flex-1 gap-2 items-center justify-start'>
      <img src={logo} alt='logo' className='size-6 md:size-8'/>
      <h1 className='text-xl md:text-2xl font-semibold'>DocHub</h1>
    </div>
  )
}

HeaderLogo.displayName = "HeaderLogo"

export {HeaderLogo}
