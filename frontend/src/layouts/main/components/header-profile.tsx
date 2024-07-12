function HeaderProfile() {
  return (
    <div className='flex md:flex-1 gap-2 items-center justify-end'>
      <img src='https://github.com/diego-manucci-bizzotto.png' alt='avatar' className='rounded-full size-10'/>
      <div className='hidden md:flex flex-col'>
        <h2 className='text-sm'>Diego Manucci Bizzotto</h2>
        <p className='text-sm text-muted-foreground'>dbizzotto@example.com</p>
      </div>
    </div>
  )
}

HeaderProfile.displayName = "HeaderProfile"

export {HeaderProfile}
