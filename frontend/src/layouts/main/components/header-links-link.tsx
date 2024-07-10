import {Link, LinkProps} from "react-router-dom";
import {Button} from "@/components/custom/button.tsx";
import {cn} from "@/lib/utils.ts";

interface HeaderLinksLinkProps extends LinkProps {
  selected: boolean
}

function HeaderLinksLink({selected, ...props} : HeaderLinksLinkProps){
  return (
    <Button variant='link' className={cn('p-0 text-base text-muted-foreground hover:no-underline hover:text-primary', selected && 'text-primary decoration-2 underline hover:underline')}>
      <Link to={props.to}>
        {props.children}
      </Link>
    </Button>
  )
}

HeaderLinksLink.displayName = "HeaderLinksLink"

export {HeaderLinksLink}
