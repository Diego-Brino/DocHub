import {useEffect, useRef} from "react";
import {lerp} from "../utils/utils.ts";

const BLOB_OFFSET = {x: -64, y: -64};
const LERP_AMOUNT = 0.03;

function MouseBlob() {
  const blobRef = useRef<HTMLDivElement>(null);
  const mousePositionRef = useRef({ x: 0, y: 0 });

  useEffect(() => {
    function moveBlob(){
      if(!blobRef.current){
        return;
      }

      const targetX = mousePositionRef.current.x + BLOB_OFFSET.x;
      const targetY = mousePositionRef.current.y + BLOB_OFFSET.y;

      const blobStyle = getComputedStyle(blobRef.current);
      const currentX = parseFloat(blobStyle.getPropertyValue('transform').split(',')[4]) || 0;
      const currentY = parseFloat(blobStyle.getPropertyValue('transform').split(',')[5]) || 0;

      const newX = lerp(currentX, targetX, LERP_AMOUNT);
      const newY = lerp(currentY, targetY, LERP_AMOUNT);

      blobRef.current.style.transform = `translate(${newX}px, ${newY}px)`;

      requestAnimationFrame(moveBlob);
    }

    function updateMousePosition(evt: MouseEvent) {
      mousePositionRef.current = {
        x: evt.clientX,
        y: evt.clientY
      };
    }

    document.addEventListener("mousemove", updateMousePosition);
    requestAnimationFrame(moveBlob);

    return () => document.removeEventListener("mousemove", updateMousePosition);
  }, []);

  return (
    <div ref={blobRef} className='absolute pointer-events-none -z-10'>
      <div
        className='relative h-44 w-44 rounded-full bg-black bg-gradient-to-bl from-[#e5d8fb] to-[#7c3aed] opacity-50 blur-2xl animate-blob'
      />
    </div>
  )
}

MouseBlob.displayName = "MouseBlob"

export {MouseBlob}
