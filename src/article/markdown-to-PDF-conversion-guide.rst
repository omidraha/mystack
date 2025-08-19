Markdown to PDF Conversion Guide
=================================


Overview
--------

This guide provides comprehensive instructions for converting Markdown files to PDF format
on Ubuntu systems using Pandoc, a universal document converter. This guide is specifically tailored for Ubuntu environments.

**Key Benefits:**

* High-quality PDF output optimized for Ubuntu
* Support for complex formatting
* Customizable styling
* Native Ubuntu package management (apt)
* Professional document appearance

Prerequisites
------------

System Requirements
~~~~~~~~~~~~~~~~~~

* **Operating System**: Ubuntu (18.04 LTS, 20.04 LTS, 22.04 LTS, or newer)
* **Package Manager**: apt (Advanced Package Tool)
* **Disk Space**: At least 500MB for LaTeX installation
* **Internet Connection**: Required for package downloads
* **User Permissions**: sudo access for package installation

Required Tools
~~~~~~~~~~~~~

* **Pandoc**: Document converter
* **LaTeX Engine**: XeLaTeX (recommended) or pdfLaTeX
* **LaTeX Packages**: Basic and additional packages for formatting

Installation
-----------

Step 1: Install Pandoc
~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Update package list
   sudo apt update

   # Install Pandoc
   sudo apt install pandoc

   # Verify installation
   pandoc --version

Step 2: Install LaTeX Engine
~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Install basic LaTeX packages
   sudo apt install texlive-base texlive-latex-base texlive-latex-recommended

   # Install additional packages for better formatting
   sudo apt install texlive-luatex texlive-pictures texlive-plain-generic
   sudo apt install texlive-extra-utils texlive-fonts-recommended texlive-xetex

   # Install XeLaTeX engine
   sudo apt install texlive-xetex

   # Install additional fonts
   sudo apt install fonts-dejavu fonts-liberation

   # Install additional useful packages
   sudo apt install texlive-latex-extra texlive-science

Step 3: Verify Installation
~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Check Pandoc version
   pandoc --version

   # Check LaTeX availability
   xelatex --version

   # Check available fonts
   fc-list | grep -i dejavu

   # Check Ubuntu version
   lsb_release -a

   # Test basic conversion
   echo "# Test" | pandoc -o test.pdf

   # Clean up test file
   rm -f test.pdf

Installation Commands
~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Complete installation for Ubuntu
   sudo apt update
   sudo apt install pandoc texlive-xetex texlive-fonts-recommended fonts-dejavu fonts-liberation texlive-latex-extra texlive-science

   # Alternative: Install all at once
   sudo apt install pandoc texlive-full fonts-dejavu fonts-liberation

Basic Conversion
---------------

Simple Conversion
~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Basic Markdown to PDF conversion
   pandoc input.md -o output.pdf

   # Example
   pandoc README.md -o README.pdf

With LaTeX Engine Specification
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Using XeLaTeX (recommended for Unicode support)
   pandoc input.md -o output.pdf --pdf-engine=xelatex

   # Using pdfLaTeX (basic LaTeX engine)
   pandoc input.md -o output.pdf --pdf-engine=pdflatex

   # Using LuaLaTeX (advanced features)
   pandoc input.md -o output.pdf --pdf-engine=lualatex

Common Options
~~~~~~~~~~~~~

.. code-block:: bash

   # Specify input format
   pandoc input.md --from=markdown -o output.pdf

   # Specify output format
   pandoc input.md --to=pdf -o output.pdf

   # Verbose output for debugging
   pandoc input.md -o output.pdf --verbose

   # Keep temporary files
   pandoc input.md -o output.pdf --keep-temp

Advanced Formatting
------------------

Document Metadata
~~~~~~~~~~~~~~~~~

Create a YAML header in your Markdown file:

.. code-block:: yaml

   ---
   title: "Document Title"
   author: "Author Name"
   date: "2024-08-18"
   subject: "Document Subject"
   keywords: [keyword1, keyword2]
   abstract: "Document abstract"
   ---

Table of Contents
~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Generate table of contents
   pandoc input.md -o output.pdf --toc

   # Specify TOC depth
   pandoc input.md -o output.pdf --toc --toc-depth=3

Page Numbers
~~~~~~~~~~~

.. code-block:: bash

   # Add page numbers
   pandoc input.md -o output.pdf -V geometry:margin=1in -V fontsize=11pt

Custom Styling
~~~~~~~~~~~~~

.. code-block:: bash

   # Use custom CSS for styling
   pandoc input.md -o output.pdf --css=style.css

   # Use custom LaTeX template
   pandoc input.md -o output.pdf --template=template.tex

Image Management
~~~~~~~~~~~~~~~

.. code-block:: bash

   # Specify resource path for images
   pandoc input.md -o output.pdf --resource-path=docs

   # Use HTML tags for image sizing
   <img src="./img/image.png" alt="Description" width="600" height="400" />

   # Control image size with percentage
   <img src="./img/image.png" alt="Description" width="50%" />

   # Use CSS styling for images
   <img src="./img/image.png" alt="Description" style="width: 400px; height: 300px;" />

Customization Options
--------------------

Font Configuration
~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Set main font
   pandoc input.md -o output.pdf -V mainfont="DejaVu Sans"

   # Set monospace font
   pandoc input.md -o output.pdf -V monofont="DejaVu Sans Mono"

   # Set font size
   pandoc input.md -o output.pdf -V fontsize=12pt

   # Multiple font options
   pandoc input.md -o output.pdf \
     -V mainfont="DejaVu Sans" \
     -V monofont="DejaVu Sans Mono" \
     -V fontsize=11pt

Page Layout
~~~~~~~~~~

.. code-block:: bash

   # Set page margins
   pandoc input.md -o output.pdf -V geometry:margin=1in

   # Custom margin settings
   pandoc input.md -o output.pdf -V geometry:"left=1in,right=1in,top=1in,bottom=1in"

   # Set paper size
   pandoc input.md -o output.pdf -V geometry:a4paper

   # Set orientation
   pandoc input.md -o output.pdf -V geometry:landscape

Document Properties
~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Set document title
   pandoc input.md -o output.pdf -V title="My Document"

   # Set author
   pandoc input.md -o output.pdf -V author="John Doe"

   # Set date
   pandoc input.md -o output.pdf -V date="2024-08-18"

   # Set subject
   pandoc input.md -o output.pdf -V subject="Document Subject"

Advanced LaTeX Options
~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Use custom LaTeX packages
   pandoc input.md -o output.pdf -H header.tex

   # Include custom LaTeX in header
   pandoc input.md -o output.pdf -H header.tex -H footer.tex

   # Use custom document class
   pandoc input.md -o output.pdf -V documentclass=article

   # Set document class options
   pandoc input.md -o output.pdf -V documentclass=article -V classoption=12pt

Troubleshooting
--------------

Common Issues
~~~~~~~~~~~~

1. LaTeX Not Found
^^^^^^^^^^^^^^^^^

.. code-block:: bash

   # Error: xelatex not found
   # Solution: Install LaTeX packages
   sudo apt install texlive-xetex texlive-fonts-recommended

2. Font Issues
^^^^^^^^^^^^^

.. code-block:: bash

   # Error: Font not found
   # Solution: Use system fonts or install fonts
   pandoc input.md -o output.pdf -V mainfont="Arial"

3. Unicode Support
^^^^^^^^^^^^^^^^^

.. code-block:: bash

   # Error: Unicode characters not displaying
   # Solution: Use XeLaTeX engine
   pandoc input.md -o output.pdf --pdf-engine=xelatex

4. Memory Issues
^^^^^^^^^^^^^^^

.. code-block:: bash

   # Error: Memory exhausted
   # Solution: Increase memory limit
   pandoc input.md -o output.pdf --pdf-engine=xelatex -V geometry:margin=1in

5. Image Path Issues
^^^^^^^^^^^^^^^^^^^

.. code-block:: bash

   # Error: Could not fetch resource img/image.png
   # Solution: Use --resource-path to specify image directory
   pandoc input.md -o output.pdf --resource-path=docs

   # Alternative: Use absolute paths in Markdown
   ![Image](./img/image.png)

   # Or use HTML tags for better control
   <img src="./img/image.png" alt="Description" width="600" height="400" />

Debug Commands
~~~~~~~~~~~~~

.. code-block:: bash

   # Check available LaTeX engines
   which xelatex
   which pdflatex
   which lualatex

   # Check Pandoc version and features
   pandoc --version

   # Test with minimal input
   echo "# Test" | pandoc -o test.pdf --pdf-engine=xelatex

   # Verbose output for debugging
   pandoc input.md -o output.pdf --verbose --pdf-engine=xelatex

Performance Optimization
~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Use faster LaTeX engine for simple documents
   pandoc input.md -o output.pdf --pdf-engine=pdflatex

   # Reduce image quality for faster processing
   pandoc input.md -o output.pdf -V geometry:margin=1in

   # Use minimal LaTeX packages
   pandoc input.md -o output.pdf --pdf-engine=xelatex -V geometry:margin=1in

Examples
--------

Example 1: Basic Document
~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Simple conversion
   pandoc document.md -o document.pdf --pdf-engine=xelatex

Example 2: Professional Document
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Professional formatting
   pandoc document.md -o document.pdf \
     --pdf-engine=xelatex \
     --toc \
     -V geometry:margin=1in \
     -V fontsize=11pt \
     -V mainfont="DejaVu Sans" \
     -V monofont="DejaVu Sans Mono" \
     -V title="Professional Document" \
     -V author="Author Name" \
     -V date="$(date +%Y-%m-%d)" \
     --resource-path=docs

Example 3: Technical Document
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Technical document with code highlighting
   pandoc technical.md -o technical.pdf \
     --pdf-engine=xelatex \
     --toc \
     --toc-depth=3 \
     -V geometry:margin=1in \
     -V fontsize=11pt \
     -V mainfont="DejaVu Sans" \
     -V monofont="DejaVu Sans Mono" \
     --highlight-style=tango \
     --resource-path=docs

Example 4: Report with Custom Styling
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Report with custom styling
   pandoc report.md -o report.pdf \
     --pdf-engine=xelatex \
     --toc \
     -V geometry:"left=1.5in,right=1in,top=1in,bottom=1in" \
     -V fontsize=12pt \
     -V mainfont="Times New Roman" \
     -V monofont="Courier New" \
     -V title="Technical Report" \
     -V author="Technical Team" \
     -V date="$(date +%B %Y)" \
     --resource-path=docs

Example 5: Document with Multiple Images
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Document with images in subdirectories
   pandoc document.md -o document.pdf \
     --pdf-engine=xelatex \
     --toc \
     -V geometry:margin=1in \
     -V fontsize=11pt \
     -V mainfont="DejaVu Sans" \
     -V monofont="DejaVu Sans Mono" \
     -V title="Document with Images" \
     -V author="Author Name" \
     -V date="$(date +%Y-%m-%d)" \
     --resource-path=docs

   # Markdown content example:
   # ![Image 1](./img/1.png)
   # ![Image 2](./img/2.png)
   # <img src="./img/3.png" alt="Custom sized image" width="400" height="300" />

Example 6: Ubuntu Automation Script
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   #!/bin/bash
   # convert_md_to_pdf.sh - Ubuntu automation script

   # Configuration
   INPUT_DIR="docs"
   OUTPUT_DIR="pdfs"
   RESOURCE_PATH="docs"

   # Check if running on Ubuntu
   if ! command -v lsb_release &> /dev/null; then
       echo "This script is designed for Ubuntu systems"
       exit 1
   fi

   # Display Ubuntu version
   echo "Running on: $(lsb_release -d | cut -f2)"

   # Create output directory if it doesn't exist
   mkdir -p "$OUTPUT_DIR"

   # Convert all Markdown files to PDF
   for file in "$INPUT_DIR"/*.md; do
       if [ -f "$file" ]; then
           filename=$(basename "$file" .md)
           echo "Converting $file to $OUTPUT_DIR/$filename.pdf"

           pandoc "$file" -o "$OUTPUT_DIR/$filename.pdf" \
               --pdf-engine=xelatex \
               --toc \
               -V geometry:margin=1in \
               -V fontsize=11pt \
               -V mainfont="DejaVu Sans" \
               -V monofont="DejaVu Sans Mono" \
               -V title="$filename" \
               -V author="$(whoami)" \
               -V date="$(date +%Y-%m-%d)" \
               --resource-path="$RESOURCE_PATH"
       fi
   done

   echo "Conversion completed!"

Best Practices
-------------

1. File Organization
~~~~~~~~~~~~~~~~~~~

* Keep Markdown files well-structured
* Use consistent naming conventions
* Organize images and assets properly
* Use relative paths for references

2. Markdown Formatting
~~~~~~~~~~~~~~~~~~~~~

* Use proper heading hierarchy
* Include table of contents when needed
* Use consistent formatting
* Test formatting before conversion

3. LaTeX Configuration
~~~~~~~~~~~~~~~~~~~~~~

* Use XeLaTeX for Unicode support
* Set appropriate margins and fonts
* Include necessary LaTeX packages
* Test with different content types

4. Performance
~~~~~~~~~~~~~

* Use appropriate LaTeX engine for content
* Optimize image sizes
* Minimize LaTeX package usage
* Use caching when possible

5. Quality Assurance
~~~~~~~~~~~~~~~~~~~

* Always verify output quality
* Check for formatting issues
* Test with different content
* Validate PDF accessibility

6. Automation
~~~~~~~~~~~~

* Create scripts for repeated conversions
* Use Makefiles for complex projects
* Implement CI/CD for automated conversion
* Version control your conversion scripts

7. Image Best Practices
~~~~~~~~~~~~~~~~~~~~~~

* Use ``--resource-path`` to specify image directories
* Prefer PNG format for better compatibility
* Use HTML tags for precise image sizing control
* Keep image paths relative to the Markdown file
* Test image rendering before final conversion
* Use descriptive alt text for accessibility

8. Ubuntu-Specific Features
~~~~~~~~~~~~~~~~~~~~~~~~~~

* Use Ubuntu-native fonts (DejaVu, Liberation)
* Leverage apt package manager for easy updates
* Use Ubuntu file permissions for security
* Utilize Ubuntu shell scripting for automation
* Take advantage of Ubuntu text processing tools
* Use Ubuntu environment variables for configuration
* Utilize Ubuntu Software Center for GUI installation

Additional Resources
-------------------

Documentation
~~~~~~~~~~~~

* `Pandoc User's Guide <https://pandoc.org/MANUAL.html>`_
* `LaTeX Documentation <https://www.latex-project.org/help/documentation/>`_
* `XeLaTeX Guide <https://en.wikibooks.org/wiki/LaTeX/XeLaTeX>`_

Tools and Extensions
~~~~~~~~~~~~~~~~~~~

* **VS Code**: Pandoc extension for Markdown preview
* **Pandocomatic**: Automated document conversion
* **Make**: Automation for complex conversions
* **Git Hooks**: Automated conversion on commit
* **Ubuntu Text Editors**: Gedit, Vim, Emacs, Nano with Pandoc integration
* **Ubuntu Software Center**: GUI installation of Pandoc and LaTeX

Community Support
~~~~~~~~~~~~~~~~

* `Pandoc GitHub Issues <https://github.com/jgm/pandoc/issues>`_
* `LaTeX Stack Exchange <https://tex.stackexchange.com/>`_
* `Pandoc Mailing List <https://groups.google.com/forum/#!forum/pandoc-discuss>`_

Quick Reference
--------------

Essential Commands
~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Basic conversion
   pandoc input.md -o output.pdf --pdf-engine=xelatex

   # With table of contents
   pandoc input.md -o output.pdf --pdf-engine=xelatex --toc

   # Professional formatting
   pandoc input.md -o output.pdf \
     --pdf-engine=xelatex \
     --toc \
     -V geometry:margin=1in \
     -V fontsize=11pt \
     -V mainfont="DejaVu Sans"

   # With images and resources
   pandoc input.md -o output.pdf \
     --pdf-engine=xelatex \
     --toc \
     -V geometry:margin=1in \
     -V fontsize=11pt \
     -V mainfont="DejaVu Sans" \
     --resource-path=docs

   # Ubuntu-optimized conversion
   pandoc input.md -o output.pdf \
     --pdf-engine=xelatex \
     --toc \
     -V geometry:margin=1in \
     -V fontsize=11pt \
     -V mainfont="DejaVu Sans" \
     -V monofont="DejaVu Sans Mono" \
     -V title="Document Title" \
     -V author="$(whoami)" \
     -V date="$(date +%Y-%m-%d)" \
     --resource-path=docs

Common Options
~~~~~~~~~~~~~

* ``--pdf-engine=xelatex``: Use XeLaTeX engine
* ``--toc``: Generate table of contents
* ``-V geometry:margin=1in``: Set page margins
* ``-V fontsize=11pt``: Set font size
* ``-V mainfont="Font Name"``: Set main font
* ``--resource-path=docs``: Specify resource directory for images
* ``--verbose``: Show detailed output

Installation Commands
~~~~~~~~~~~~~~~~~~~~

.. code-block:: bash

   # Complete installation for Ubuntu
   sudo apt update
   sudo apt install pandoc texlive-xetex texlive-fonts-recommended fonts-dejavu fonts-liberation texlive-latex-extra texlive-science

   # Alternative: Install all at once
   sudo apt install pandoc texlive-full fonts-dejavu fonts-liberation

----

*This guide covers the essential steps for converting Markdown files to PDF using Pandoc
on Ubuntu systems.
For more advanced features and customization options, refer to the official Pandoc documentation.*
