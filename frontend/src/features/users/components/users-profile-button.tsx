function UsersProfileButton() {

  return (
    <div className='flex md:flex-1 gap-4 items-center justify-end'>
      <div className='flex gap-2'>
        <img src='https://github.com/diego-manucci-bizzotto.png' alt='avatar' className='rounded-full size-10'/>
        <div className='hidden md:flex flex-col'>
          <h2 className='text-sm'>Diego Manucci Bizzotto</h2>
          <p className='text-sm text-muted-foreground'>dbizzotto@example.com</p>
        </div>
      </div>
    </div>
  )
}

UsersProfileButton.displayName = 'UsersProfileButton'

export {UsersProfileButton}