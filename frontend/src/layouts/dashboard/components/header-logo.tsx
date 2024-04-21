import logo from "@/assets/logo.svg";

function HeaderLogo() {
  return (
    <div className='flex items-center justify-center w-[60px] md:w-auto p-0 md:p-6 gap-2 md:gap-2 border-b sm:border-r bg-muted/40'>
      <img src={logo} alt="logo" className='size-8 md:size-10'/>
      <h1 className='hidden md:inline text-3xl font-semibold'>DocHub</h1>
    </div>
  )
}

HeaderLogo.displayName = "HeaderLogo"

export {HeaderLogo}