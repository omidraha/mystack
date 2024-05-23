WSL
---


.. code-block:: bash

    wsl.exe --install

.. code-block:: bash

    wsl.exe --update

.. code-block:: bash

    wsl -l
    wsl

.. code-block:: bash

    wsl --version

    WSL version: 2.2.4.0
    Kernel version: 5.15
    WSLg version: 1.0.61
    MSRDC version: 1.2
    Direct3D version: 1.611
    DXCore version: 10.0
    Windows version: 10.0


.. code-block:: bash

    bcdedit /set hypervisorlaunchtype auto

.. code-block:: bash

    DISM /Online /Enable-Feature /All /FeatureName:Microsoft-Hyper-V

.. code-block:: bash

    dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart



.. code-block:: bash

    wsl --set-version Ubuntu 2

    For information on key differences with WSL 2 please visit https://aka.ms/wsl2
    Conversion in progress, this may take a few minutes.
    WSL2 is not supported with your current machine configuration.
    Please enable the "Virtual Machine Platform" optional component and ensure virtualization is enabled in the BIOS.
    Enable "Virtual Machine Platform" by running: wsl.exe --install --no-distribution
    For information please visit https://aka.ms/enablevirtualization
    Error code: Wsl/Service/CreateVm/HCS/HCS_E_HYPERV_NOT_INSTALLED


The newest version of WSL uses a subset of Hyper-V architecture to enable its virtualization.

This subset is provided as an optional component named "Virtual Machine Platform," available on all Desktop SKUs.


https://learn.microsoft.com/en-us/windows/wsl/faq#does-wsl-2-use-hyper-v-

https://learn.microsoft.com/en-us/windows/wsl/install-manual#step-4---download-the-linux-kernel-update-package

https://github.com/microsoft/WSL/issues/5363

https://github.com/microsoft/WSL/issues/5650


