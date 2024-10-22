`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company:  ETH Zurich
// Engineer: Frank K. Gurkaynak
//
// Create Date:
// Design Name:    MIPS processor
// Module Name:    bad_ALU
// Project Name:   Digital Circuits Lab Exercuse
// Target Devices:
// Tool versions:
// Description:    This is a poor attempt at writing an ALU
// Dependencies:
//
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
//
//////////////////////////////////////////////////////////////////////////////////

module bad_ALU(input [31:0] a,
               input [31:0] b,
               input [3:0] aluop,
               output [31:0] result,
               output zero);

reg [31:0] slt;
reg [31:0] logicsel;


reg [31:0] alu_val; //we need to declare this reg
reg [31:0] diff;
wire ss0;  wire ss1; wire ss2; wire ss3;

assign {ss0,ss1,ss2,ss3} = aluop; // make assigns

// define the logic function
always @ (*)
begin 
    if (ss2 ==0)                // XX0X
        if (ss3 == 0)           // XX00
            logicsel = a & b;   
        else                    // XX01
            logicsel = a | b;
    else                        // XX1X
        if (ss3 == 1)           // XX10
            logicsel = ~(a | b);
        else                    // XX11
            logicsel = a ^ b;
end

// slt
always @ (aluop)
    if (aluop == 4'b1010)
    begin
        diff <= a - b;			// calculate the difference
        slt <= 32'b0;				// default value
        if (diff[31] == 1'b0)
            slt <= 32'b1;		   	// if MSB is 1 slt is 1
    end

// operation
always @ (*)
begin
    case (aluop)
        4'b0000 : alu_val = a + b;
        4'b0010 : alu_val = a - b;
        4'b1010 : alu_val = slt;
        default : alu_val = logicsel;
    endcase
end

assign result = alu_val;
assign zero   = (alu_val == 32'b0) ? 1 : 0;

endmodule
