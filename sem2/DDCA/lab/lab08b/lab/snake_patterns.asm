    ### Aanjhan Ranganathan <raanjhan@inf.ethz.ch>
    ### For Lab 8 Digital Technik
    ### 2014

    ###   I/O addresses Reference
    ###  compatible to the compact modelling
    ###  0x00007FF0   LED output

.data   
pattern:
    .word
        0x00200000, 0x00004000, 0x00000080, 0x00000001,
        0x00000002, 0x00000004, 0x00000008, 0x00000400,
        0x00020000, 0x01000000, 0x02000000, 0x04000000
            
loopcnt:
    .word
        0x000f4242, 0x001e8484, 0x002dC6c6, 0x003d0908

#################################################################################################################################################

.text   
main:
    # reg to hold starting address of speed
    lw      $t1,    0x7ff4($0)                      # read the value from the switches (speed controll)
    # lw      $t6,    0x7ff4($0)                      # read the value from the switches (speed controll)
    lw      $t3,    loopcnt($t1)                    # initialize a  large loopcounter (so that the snake does not crawl SUPERFAST)
    addi    $t5,    $0,         48                  # initialize the length of the display pattern (in bytes)

restart:    
    addi    $t4,    $0,         0                   # display counter

forward:    
    beq     $t5,    $t4,        restart
    lw      $t0,    0($t4)
    sw      $t0,    0x7ff0($0)                      # send the value to the display

    addi    $t4,    $t4,        4                   # increment to the next address
    addi    $t2,    $0,         0                   # clear $t2 counter

wait:       
    lw      $t1,    0x7ff0($0)                      # read speed controll value again
    lw      $t3,    loopcnt($t1)                    # read the loop counter         ### NOT ALLIGNED !!!!!
    beq     $t2,    $t3,        forward
    addi    $t2,    $t2,        1                   # increment counter
    j       wait



    ### End of file

